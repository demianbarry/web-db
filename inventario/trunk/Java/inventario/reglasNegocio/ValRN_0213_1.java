/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import inventario.models.ComprobanteMovimientoArticuloModel;
import inventario.models.ConversionesModel;
import inventario.models.DetalleRCModel;
import inventario.models.DetalleSCModel;
import inventario.models.MovimientoArticuloModel;
import inventario.models.OrdenesCompraModel;
import inventario.models.RecepcionesComprasModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Hashtable;

import com.salmonllc.properties.Props;
import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Juan Manuel
 * 
 *         Regla de negocio asociada a la confirmaci�n de una recepci�n
 * 
 */
public final class ValRN_0213_1 extends ValidadorReglasNegocio {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 * java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {
		Statement st = null;
		try {
			RecepcionesComprasModel ds = (RecepcionesComprasModel) obj;

			if (!UsuarioRolesModel.isRolUsuario(ds.getCurrentWebsiteUserId(),
					Constants.USER_ENCARGADO_ALMACEN))
				throw new DataStoreException(
						"Usted no est� autorizado para confirmar recepciones.");

			DetalleRCModel detalles = new DetalleRCModel("inventario");
			DetalleSCModel detallesSC = new DetalleSCModel("inventario");
			OrdenesCompraModel ordenes = new OrdenesCompraModel("inventario");
			ordenes.retrieve(conn);
			int recepcionCompraId = ds.getRecepcionesComprasRecepcionCompraId();

			detalles.setOrderBy("detalles_rc.almacen_id");
			detalles.retrieve("detalles_rc.recepcion_compra_id ="
					+ recepcionCompraId);
			detalles.gotoFirst();
			ComprobanteMovimientoArticuloModel comprobanteMovimiento = new ComprobanteMovimientoArticuloModel(
					"inventario");
			MovimientoArticuloModel movimiento = new MovimientoArticuloModel(
					"inventario");
			int tipoMovimiento = Props.getProps("inventario", null)
					.getIntProperty("TipoMovimientoRecepciones");
			if (tipoMovimiento == -1) {
				msg
						.append("No se encontro propiedad TipoMovimientoRecepciones en archivo de configuraci�n");
				return false;
			}

			int almacen_id = 0;

			detallesSC.retrieve();
			Hashtable<Integer, Integer> almacen_comprobantes = new Hashtable<Integer, Integer>();

			float cantidad_recibida = 0;

			for (int row = 0; row < detalles.getRowCount(); row++) {
				detalles.gotoRow(row);
				if (almacen_id != detalles.getDetallesRcAlmacenId()) {
					almacen_id = detalles.getDetallesRcAlmacenId();
					comprobanteMovimiento.gotoRow(comprobanteMovimiento
							.insertRow());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloAlmacenId(almacen_id);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloEstado("0010.0002");
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloFecha(ds
									.getRecepcionesComprasFecha());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloObservaciones(ds
									.getRecepcionesComprasObservaciones());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloRecepcionCompraId(recepcionCompraId);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloTipoMovimientoArticuloId(tipoMovimiento);
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloUserIdPreparador(ds
									.getRecepcionesComprasUserIdCompleta());
					comprobanteMovimiento
							.setComprobanteMovimientoArticuloUserIdRetira(ds
									.getRecepcionesComprasUserIdRecibe());
					almacen_comprobantes
							.put(
									almacen_id,
									comprobanteMovimiento
											.getComprobanteMovimientoArticuloComprobanteMovimientoId());
				}
				detallesSC.filter("detalle_sc.detalle_SC_id =="
						+ detalles.getDetallesRcDetalleScId());
				detallesSC.gotoFirst();
				cantidad_recibida = detallesSC.getDetalleScCantidadRecibida()
						+ (float) detalles.getDetallesRcCantidad();

				detallesSC.setDetalleScCantidadRecibida(cantidad_recibida);

				// a continuaci�n, chequeo si todos los art�culos de la OC han
				// sido recibidos
				boolean setFechaOCRecibidaCompleta = true;

				// obtengo la tasa especificada para considerar recepcionado un
				// art�culo
				float tasaRecepcionCompletaArticulo = Float.parseFloat(Props
						.getProps("inventario", null).getProperty(
								Constants.TASA_RECEPCION_COMPLETA_ARTICULO));

				// chequeo los detalles de toda la OC
				detallesSC.filter("detalle_SC.orden_compra_id == "
						+ detallesSC.getDetalleScOrdenCompraId());				
				for (int row1 = 0; row1 < detallesSC.getRowCount(); row1++) {
					// si en un detalle la tasa de recepci�n es menor a la establecida, seteo el flag en false
					if (detallesSC.getDetalleScCantidadRecibida(row1)
							/ detallesSC.getDetalleScCantidadPedida(row1) < tasaRecepcionCompletaArticulo) {
						setFechaOCRecibidaCompleta = false;
					}
				}

				// si el flag est� en 'true' es que todos los detalles est�n recibidos
				if (setFechaOCRecibidaCompleta) {
					ordenes.filter("ordenes_compra.orden_compra_id == "
							+ detallesSC.getDetalleScOrdenCompraId());
					ordenes.gotoFirst();
					ordenes.setOrdenesCompraFechaEntregaCompleta(new Date(
							Calendar.getInstance().getTimeInMillis()));
					ordenes.update(conn);
				}

			}
			detallesSC.filter(null);
			detallesSC.update(conn);
			detallesSC.resetStatus();
			comprobanteMovimiento.update(conn);
			comprobanteMovimiento.resetStatus();

			st = conn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT sum(detalles_rc.cantidad_recibida) cantidad_recibida, sum(detalles_rc.cantidad_excedencia) cantidad_excedencia, detalle_sc.articulo_id, detalles_rc.almacen_id, solicitudes.proyecto_id, detalle_sc.tarea_id, cantidad_pedida, detalles_rc.unidad_medida_id, monto_unitario "
							+ "FROM detalles_rc detalles_rc "
							+ "INNER JOIN recepciones_compras ON detalles_rc.recepcion_compra_id = recepciones_compras.recepcion_compra_id  "
							+ "INNER JOIN detalle_sc detalle_sc ON detalles_rc.detalle_sc_id = detalle_sc.detalle_SC_id  "
							+ "INNER JOIN almacenes ON detalles_rc.almacen_id = almacenes.almacen_id  "
							+ "INNER JOIN articulos articulos ON detalle_sc.articulo_id = articulos.articulo_id "
							+ "INNER JOIN unidades_medida unidades_medida ON detalle_sc.unidad_medida_id = unidades_medida.unidad_medida_id "
							+ "INNER JOIN solicitudes_compra solicitudes ON detalle_sc.solicitud_compra_id = solicitudes.solicitud_compra_id "
							+ "WHERE detalles_rc.recepcion_compra_id = "
							+ recepcionCompraId
							+ " GROUP BY almacen_id, articulo_id ORDER BY almacen_id");

			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			int articulo_id = 0;
			int comprobante_movimiento_id = 0;
			double cantidad_entregada = 0;
			int unidad_medida_id = 0;

			while (rs.next()) {
				almacen_id = rs.getInt("almacen_id");
				articulo_id = rs.getInt("articulo_id");
				unidad_medida_id = rs.getInt("unidad_medida_id");
				comprobanteMovimiento
						.setFindExpression("comprobante_movimiento_articulo.almacen_id == "
								+ almacen_id);
				comprobanteMovimiento.findFirst();
				comprobante_movimiento_id = comprobanteMovimiento
						.getComprobanteMovimientoArticuloComprobanteMovimientoId();

				movimiento.gotoRow(movimiento.insertRow());
				movimiento.setMovimientoArticuloArticuloId(articulo_id);
				cantidad_entregada = rs.getDouble("cantidad_recibida")
						+ rs.getDouble("cantidad_excedencia");
				movimiento
						.setMovimientoArticuloCantidadEntregada(cantidad_entregada);
				movimiento
						.setMovimientoArticuloCantidadSolicitada(cantidad_entregada);
				movimiento
						.setMovimientoArticuloComprobanteMovimientoId(comprobante_movimiento_id);
				movimiento.setMovimientoArticuloProyectoId(rs
						.getInt("proyecto_id"));
				movimiento
						.setMovimientoArticuloUnidadMedidaId(unidad_medida_id);

				movimiento.setMovimientoArticuloTareaId(rs.getInt("tarea_id"));
				AtributosEntidadModel.setValorAtributoObjeto(String
						.valueOf(ConversionesModel.getUnidadConvertida(
								articulo_id, unidad_medida_id, rs
										.getInt("monto_unitario"), conn)),
						Constants.ARTICULO_PRECIO_REPOSICION, articulo_id,
						"TABLA", "art�culos");
			}
			movimiento.update(conn);
			movimiento.resetStatus();
			int accion = Props.getProps("inventario", null).getIntProperty(
					Constants.ACCION_CONFIRMA_MOVIMIENTO);

			for (int row = 0; row < comprobanteMovimiento.getRowCount(); row++) {
				comprobanteMovimiento.gotoRow(row);
				comprobanteMovimiento.setCurrentWebsiteUserId(ds
						.getCurrentWebsiteUserId());
				if (accion > 0)
					comprobanteMovimiento.ejecutaAccion(accion, "0010", "", ds
							.getCurrentWebsiteUserId(),
							"comprobante_movimiento_articulo", conn, false);
				else
					throw new DataStoreException(
							"No se ha indicado en el archivo System.properties la acci�n correspondiente a la confirmaci�n de un movimiento de inventario.");
			}

		} catch (DataStoreException ex) {
			msg.append(ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg
					.append("Ocurri� un error de SQL mientras se procesaba la acci�n: "
							+ ex.getMessage());
			return false;
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException ex) {
				msg
						.append("Ocurri� un error de SQL mientras se cerraba el Statement: "
								+ ex.getMessage());
			}
		}
		return true;
	}
}
