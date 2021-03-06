/**
 *
 */
package inventario.reglasNegocio;

import infraestructura.controllers.Constants;
import infraestructura.models.AtributosConfiguracionModel;
import infraestructura.models.AtributosEntidadModel;
import infraestructura.models.UsuarioRolesModel;
import infraestructura.reglasNegocio.ValidadorReglasNegocio;
import infraestructura.utils.DeterminaConfiguracionServicio;
import inventario.models.CadenasAprobacionModel;
import inventario.models.DetalleSCModel;
import inventario.models.InstanciasAprobacionModel;
import inventario.models.OrdenesCompraModel;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Iterator;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;

/**
 * @author Francisco
 * 
 * Regla de Negocio.
 * 
 * Objeto asociado: ordenes_compra
 * 
 * Transici�n: "Generada" -> "Completa"
 * 
 * Acci�n: "Completar"
 * 
 * Ejecuta validaciones asociadas con la acci�n de completar una orden de compra.
 * 
 * Chequea:
 * 1) Rol del usuario que ejecuta la transici�n debe ser COMPRADOR.
 * 2) El nro. de detalles en la orden de ser mayor que cero
 * 3) El proveedor debe estar especificado.
 * 4) La fecha estimada de entrega debe estar indicada.
 * 4) La condici�n de compra debe estar especificada.
 * 5) Todos los montos unitarios deben ser mayores que cero.
 * 
 * Si todos las verificaciones son exitosas se genera una cadena de aprobaci�n 
 * para el firmado de la orden de compra.
 * 
 */
public final class ValRN_0208_1 extends ValidadorReglasNegocio {
	
	private static final String NOMBRE_OBJETO = "ordenes_compra";

	/*
	 * (non-Javadoc)
	 * 
	 * @see infdev.reglasNegocio.validadorReglasNegocio#esValido(java.lang.Object,
	 *      java.lang.String)
	 */
	public boolean esValido(Object obj, StringBuilder msg, DBConnection conn) {

		try {
			OrdenesCompraModel ds = (OrdenesCompraModel) obj;
			DetalleSCModel detalles = new DetalleSCModel("inventario",
					"inventario");

			int ordenCompraId = ds.getOrdenesCompraOrdenCompraId();
			
			// chequea el rol o identidad del usuario que va a completa el OC
			int currentUser = ds.getCurrentWebsiteUserId();
			if (!UsuarioRolesModel.isRolUsuario(currentUser, Constants.USER_COMPRADOR)) {
				msg.append("Debe ser COMPRADOR o el comprador original para completar una Orden de compra.");
				return false;
			}		
			
			// Guarda cambios realizados a la orden
			if (ds.getRow() == -1)
				return false;
			ds.update(conn);
			
			// el n�mero de detalles de la OC debe ser > 0 
			if (detalles.estimateRowsRetrieved(conn, "detalle_sc.orden_compra_id = " + ordenCompraId) == 0) {
				msg.append("Debe especificar por lo menos un detalle de una SC a comprar");
				return false;
			} else {
				// guarda los cambios realizados a los detalles
				detalles.retrieve(conn, "detalle_sc.orden_compra_id = " + ordenCompraId);
				if (detalles.getRow() != -1) {
					detalles.update(conn);
				}
			}
			
			if (ds.getOrdenesCompraEntidadIdProveedor() == 0) {
				msg.append("Debe especificar el proveedor");
				return false;
			}
			
			if (ds.getOrdenesCompraFechaEstimadaEntrega() == null) {
				msg.append("Debe especificar la fecha estimada de entrega");
				return false;
			}
			
			if (ds.getOrdenesCompraCondicionCompraId() == 0) {
				msg.append("Debe especificar la condici�n de compra de la OC");
				return false;
			}
			
			// todos los montos deben ser > 0
			if (!detalles.chequeaTotalesDetallesOrden(ordenCompraId)) {
				msg.append("Debe indicar el monto unitario de todos los articulos antes de completar la solicitud");
				return false;
			}

			InstanciasAprobacionModel instancia = new InstanciasAprobacionModel(
					"inventario", "inventario");
			instancia.retrieve(
					"instancias_aprobacion.nombre_objeto = '" + NOMBRE_OBJETO + "' AND " + 
					"instancias_aprobacion.objeto_id =" + ordenCompraId +
					" AND instancias_aprobacion.estado = 0007.0001"
					);
			if (instancia.gotoFirst()) {
				return true;
			}

			String valorAtributo = AtributosEntidadModel
					.getValorAtributoObjeto("CONFIGURACION_ID",
							ordenCompraId, "TABLA", "ordenes_compra");

			int configuracion_id;

			if (valorAtributo != null && valorAtributo != "0")
				configuracion_id = Integer.parseInt(valorAtributo);
			else {
				configuracion_id = DeterminaConfiguracionServicio
						.determinaConfiguracion(AtributosConfiguracionModel
								.determinaAtributosConfiguracion(ds
										.getEsquemaConfiguracionId(),
										ordenCompraId, "TABLA",
										"ordenes_compra"));

				AtributosEntidadModel.setValorAtributoObjeto(String
						.valueOf(configuracion_id), "CONFIGURACION_ID",
						ordenCompraId, "TABLA", "ordenes_compra");
			}

			CadenasAprobacionModel cadena = new CadenasAprobacionModel(
					"inventario", "inventario");

			cadena.retrieve("configuracion_id = " + configuracion_id);

			if (!cadena.gotoFirst()) {
				msg.append("No se recupero ninguna cadena de aprobaci�n!");
				return false;
			}

			Iterator<Integer> siguientesFirmantes = cadena
					.getSiguientesFirmantes(false, 0);

			if (siguientesFirmantes == null) {
				msg.append("No se recuper� la lista de firmantes correspondiente");
				return false;
			}

			while (siguientesFirmantes.hasNext()) {
				instancia.gotoRow(instancia.insertRow());
				
				instancia.setInstanciasAprobacionEstado("0007.0001");
				instancia.setInstanciasAprobacionFechaEntrada(new Timestamp(
						(Calendar.getInstance().getTimeInMillis())));
				instancia.setInstanciasAprobacionNombreObjeto("ordenes_compra");
				instancia.setInstanciasAprobacionObjetoId(ordenCompraId);
				instancia.setInstanciasAprobacionUserFirmante(siguientesFirmantes.next());
				instancia.setInstanciasAprobacionOrden(cadena.getOrder());
			}
			instancia.update(conn);
			
			ds.setOrdenesCompraFecha(new Timestamp(Calendar
					.getInstance().getTimeInMillis()));
			ds.update(conn);
			
			return true;
		} catch (DataStoreException ex) {
			msg
					.append("Ocurri� un error en el DataStore mientras se procesaba su aprobaci�n: "
							+ ex.getMessage());
			return false;
		} catch (SQLException ex) {
			msg
					.append("Ocurri� un error de SQL mientras se procesaba su aprobaci�n: "
							+ ex.getMessage());
			return false;
		}
	}

}
