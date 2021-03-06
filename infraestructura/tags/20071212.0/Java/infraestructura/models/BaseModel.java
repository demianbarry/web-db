package infraestructura.models;

import infraestructura.reglasNegocio.ValidadorReglasNegocio;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Hashtable;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

public abstract class BaseModel extends DataStore {
	private static Hashtable<String,AuditaEstadosCircuitosModel> instanciasDeAuditoria;

	/**
	 * Create a new ProyectoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 */
	public BaseModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new ProyectoModel object.
	 *
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public BaseModel(String appName, String profile) {
		super(appName, profile);
	}

	/**
     * @param row n�mero de fila sobre la cual se desea hacer la operacion
     * @param accion accion grabada en la tabla de tarnsici�n de estados
     * @param circuito circuito al cual pertenece la acci�n. Permite recuperar la columna de estado
     * @param pagina P�gina de la cual se realiza la operaci�n
     * Ejecuta la acci�n dada para el informe de devoluci�n.
     * Concentra TODAS las acciones posibles para un informe de devolici�n
     */
    public void ejecutaAccion(int row,int accion, String circuito, String remoteAddr, int userId, String nombre_tabla, DBConnection conn, boolean batchInserts) throws DataStoreException{
   	 if (!gotoRow(row)){
   		throw new DataStoreException("Fila " + row + " fuera de contexto");
   	 }
   	 ejecutaAccion(accion,circuito, remoteAddr, userId, nombre_tabla, conn, batchInserts);
    }
    /**
    * @param accion accion grabada en la tabla de tarnsici�n de estados
    * @param circuito circuito al cual pertenece la acci�n. Permite recuperar la columna de estado
    * @param pagina P�gina de la cual se realiza la operaci�n
    * Ejecuta la acci�n dada para el registro actual.
    * Concentra TODAS las acciones posibles para un registro
    */
   public void ejecutaAccion(int accion, String circuito, String remoteAddr, int userId, String nombre_tabla, DBConnection conn, boolean batchInserts) throws DataStoreException{
		String estado_actual = null;
		String proximo_estado = null;
		String nombre_accion = null;
		String columna_circuito = null;
		String validador = null;
		StringBuilder resultado;
		boolean ok = false;

		// verifico si est� conectado un usuario

		if (userId == 0){
			throw new DataStoreException("Debe estar conectado como un usuario de la aplicaci�n...");
		}

		// chequeo que el registro este en el contexto correspondiente
		if (getRow() == -1) {
			throw new DataStoreException("No hay ning�n registro seleccionado");
		}


		// recupero toda la informaci�n requerida para evaluar la acci�n
		// correspondiente
		// y ejecutarla
		try {
			AplicaCircuitoModel aplicaCircuito = new AplicaCircuitoModel("infraestructura","infraestructura");
			aplicaCircuito.retrieve("aplica_circuito.circuito = '"+ circuito + "'");

			if (aplicaCircuito.gotoFirst()) {
				columna_circuito = aplicaCircuito.getAplicaCircuitoNombreDetalle();
			} else {
				return;
			}

			estado_actual = getEstadoActual();

			// recupero el pr�ximo estado y el nombre de la acci�n en funci�n de
			// la acci�n
			TransicionEstadoModel transicion = new TransicionEstadoModel("infraestructura","infraestructura");
			transicion.retrieve("estados_origen.circuito = '"+circuito+ "' and estado_origen = '"+estado_actual +"' and transicion_estados.accion = "+Integer.toString(accion) );

			if (transicion.gotoFirst()) {
				proximo_estado = transicion.getTransicionEstadosEstadoDestino();
				nombre_accion = transicion.getTransicionEstadosPromptAccion();
				validador = transicion.getTransicionEstadosValidador();
			}

			// Verifica rutina de validaci�n din�mica
			try {
				if (validador != null && validador.length() > 0 && !validador.equalsIgnoreCase("No Validar")){
					Class claseVal = Class.forName(validador);
					ValidadorReglasNegocio val = (ValidadorReglasNegocio) claseVal.newInstance();

					resultado = new StringBuilder("");
					if (val.esValido(this,resultado,conn)) {
						ok = true;
					} else{
						ok = false;
						throw new DataStoreException(resultado.toString());
					}
				}
				else if (validador == null || validador.length() == 0){
					throw new DataStoreException(nombre_accion + " -- No tiene implementada Validaci�n. Se requiere especificar Validaci�n");
				}
				else if (validador.equalsIgnoreCase("No Validar")){
					// La regla NO requiere de validaci�n
					ok = true;
				}
				else {
					throw new DataStoreException(nombre_accion + " -- Situaci�n no prevista");
				}
			} catch (ClassNotFoundException e) {
				throw new DataStoreException(e.getMessage());
			} catch (InstantiationException e) {
				throw new DataStoreException(e.getMessage());
			} catch (IllegalAccessException e) {
				throw new DataStoreException(e.getMessage());
			}


			// si hay cambio de estado al finalizar, independientemente de la
			// acci�n paso al pr�ximo
			// estado en el registro y actualizo
			// Se inserta tambi�n el registro de auditor�a correspondiente s�lo
			// si cambi� estado
			if (ok && !estado_actual.equalsIgnoreCase(proximo_estado)) {

				setString(nombre_tabla+"." + columna_circuito,proximo_estado);
				update(conn);

				AuditaEstadosCircuitosModel auditoriaModel;

				if (instanciasDeAuditoria == null)
					instanciasDeAuditoria = new Hashtable<String, AuditaEstadosCircuitosModel>();

				if (instanciasDeAuditoria.containsKey(nombre_tabla))
					auditoriaModel = instanciasDeAuditoria.get(nombre_tabla);
				else {
					auditoriaModel = new AuditaEstadosCircuitosModel("infraestructura","infraestructura");
					instanciasDeAuditoria.put(nombre_tabla, auditoriaModel);
				}

				auditoriaModel.setBatchInserts(batchInserts);

				auditoriaModel.gotoRow(auditoriaModel.insertRow());
				auditoriaModel.setAuditaEstadosCircuitosCircuito(circuito);
				auditoriaModel.setAuditaEstadosCircuitosFecha(new Timestamp((new Date()).getTime()));
				auditoriaModel.setAuditaEstadosCircuitosAEstado(proximo_estado);
				auditoriaModel.setAuditaEstadosCircuitosDeEstado(estado_actual);
				auditoriaModel.setAuditaEstadosCircuitosUserId(userId);
				auditoriaModel.setAuditaEstadosCircuitosNombreTabla(getAppName()+"."+nombre_tabla);
				auditoriaModel.setAuditaEstadosCircuitosRegistroId(getIdRegistro());
				auditoriaModel.setAuditaEstadosCircuitosHost(remoteAddr);
				auditoriaModel.setAuditaEstadosCircuitosAccion(accion);

				auditoriaModel.update(conn);
			}

			if (!ok)
				throw new DataStoreException("Fall� la validaci�n del registro");

		} catch (DataStoreException e) {
			throw new DataStoreException(e.getMessage());
		}
		catch (SQLException e) {
			throw new DataStoreException(nombre_accion + " -- " + e.getMessage());
		}
	}

   public abstract String getEstadoActual() throws DataStoreException;


/**
    * Se debe definir en cada Model qu� valor representa el Id del registro
    * @return el Id del regsitro actual
    */
   public abstract int getIdRegistro() throws DataStoreException;

}
