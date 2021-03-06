package infraestructura.models;

import com.salmonllc.sql.DataStore;
import com.salmonllc.sql.DataStoreException;

//$CUSTOMIMPORTS$
//Put custom imports between these comments, otherwise they will be overwritten if the model is regenerated

//$ENDCUSTOMIMPORTS$

/**
 * WebsiteUserModel: A SOFIA generated model
 */
public class WebsiteUserModel extends DataStore {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7972358450220671348L;
	// constants for columns
	public static final String WEBSITE_USER_USER_ID = "website_user.user_id";
	public static final String WEBSITE_USER_LOGIN_NAME = "website_user.login_name";
	public static final String WEBSITE_USER_LOGIN_PASSWORD = "website_user.login_password";
	public static final String WEBSITE_USER_NIVEL_VISIBILIDAD = "website_user.nivel_visibilidad";
	public static final String WEBSITE_USER_APEYNOM = "website_user.nombre_completo";
	public static final String WEBSITE_USER_EMAIL = "website_user.email";
	public static final String WEBSITE_USER_NRO_LEGAJO = "website_user.nro_legajo";
	public static final String WEBSITE_USER_NRO_COMPRADOR = "website_user.nro_comprador";

	// $CUSTOMVARS$
	// Put custom instance variables between these comments, otherwise they will
	// be overwritten if the model is regenerated
	public static final String LEGAJOS_APEYNOM = "legajos.APEYNOM";
	public static final String LEGAJO_APELLIDO = "legajo.APELLIDO";

	// $ENDCUSTOMVARS$

	/**
	 * Create a new WebsiteUserModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 */
	public WebsiteUserModel(String appName) {
		this(appName, null);
	}

	/**
	 * Create a new WebsiteUserModel object.
	 * 
	 * @param appName
	 *            The SOFIA application name
	 * @param profile
	 *            The database profile to use
	 */
	public WebsiteUserModel(String appName, String profile) {
		super(appName, profile);
			// add aliases
			addTableAlias(computeTableName("infraestructura.website_user"), "website_user");
			addTableAlias(computeTableName("infraestructura.legajos"), "legajos");

			// add columns
			addColumn(computeTableName("website_user"), "user_id",
					DataStore.DATATYPE_INT, true, true, WEBSITE_USER_USER_ID);
			addColumn(computeTableName("website_user"), "login_name",
					DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_LOGIN_NAME);
			addColumn(computeTableName("website_user"), "login_password",
					DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_LOGIN_PASSWORD);
			addColumn(computeTableName("website_user"), "nivel_visibilidad",
					DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NIVEL_VISIBILIDAD);
			addColumn(computeTableName("website_user"), "nombre_completo",
					DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_APEYNOM);
			addColumn(computeTableName("website_user"), "email",
					DataStore.DATATYPE_STRING, false, true, WEBSITE_USER_EMAIL);
			addColumn(computeTableName("website_user"), "nro_legajo",
					DataStore.DATATYPE_INT, false, true,
					WEBSITE_USER_NRO_LEGAJO);
			addColumn(computeTableName("website_user"), "nro_comprador",
					DataStore.DATATYPE_STRING, false, true,
					WEBSITE_USER_NRO_COMPRADOR);
			addColumn(computeTableName("legajos"), "APEYNOM",
					DataStore.DATATYPE_STRING, false, false, LEGAJOS_APEYNOM);
			addColumn(computeTableName("legajos"), "APELLIDO",
					DataStore.DATATYPE_STRING, false, false, LEGAJO_APELLIDO);

			// add joins
			//addJoin(computeTableAndFieldName("website_user.nro_legajo"), computeTableAndFieldName("legajos.nro_legajo"), true);			
			addJoin(computeTableAndFieldName("website_user.nro_legajo"), computeTableAndFieldName("legajos.NRO_LEGAJO"), true);
			
			// $CUSTOMCONSTRUCTOR$
			// Put custom constructor code between these comments, otherwise it
			// be
			// overwritten if the model is regenerated

			// $ENDCUSTOMCONSTRUCTOR$

	}

	/**
	 * Retrieve the value of the website_user.user_id column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserUserId() throws DataStoreException {
		return getInt(WEBSITE_USER_USER_ID);
	}

	/**
	 * Retrieve the value of the website_user.user_id column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserUserId(int row) throws DataStoreException {
		return getInt(row, WEBSITE_USER_USER_ID);
	}

	/**
	 * Set the value of the website_user.user_id column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserUserId(int newValue) throws DataStoreException {
		setInt(WEBSITE_USER_USER_ID, newValue);
	}

	/**
	 * Set the value of the website_user.user_id column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserUserId(int row, int newValue)
			throws DataStoreException {
		setInt(row, WEBSITE_USER_USER_ID, newValue);
	}

	/**
	 * Retrieve the value of the website_user.login_name column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserLoginName() throws DataStoreException {
		return getString(WEBSITE_USER_LOGIN_NAME);
	}

	/**
	 * Retrieve the value of the website_user.login_name column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserLoginName(int row) throws DataStoreException {
		return getString(row, WEBSITE_USER_LOGIN_NAME);
	}

	/**
	 * Set the value of the website_user.login_name column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserLoginName(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_LOGIN_NAME, newValue);
	}

	/**
	 * Set the value of the website_user.login_name column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserLoginName(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_LOGIN_NAME, newValue);
	}

	/**
	 * Retrieve the value of the website_user.login_password column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserLoginPassword() throws DataStoreException {
		return getString(WEBSITE_USER_LOGIN_PASSWORD);
	}

	/**
	 * Retrieve the value of the website_user.login_password column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserLoginPassword(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_LOGIN_PASSWORD);
	}

	/**
	 * Set the value of the website_user.login_password column for the current
	 * row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserLoginPassword(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_LOGIN_PASSWORD, newValue);
	}

	/**
	 * Set the value of the website_user.login_password column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserLoginPassword(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_LOGIN_PASSWORD, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nivel_visibilidad column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNivelVisibilidad() throws DataStoreException {
		return getString(WEBSITE_USER_NIVEL_VISIBILIDAD);
	}

	/**
	 * Retrieve the value of the website_user.nivel_visibilidad column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNivelVisibilidad(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_NIVEL_VISIBILIDAD);
	}

	/**
	 * Set the value of the website_user.nivel_visibilidad column for the
	 * current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNivelVisibilidad(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_NIVEL_VISIBILIDAD, newValue);
	}

	/**
	 * Set the value of the website_user.nivel_visibilidad column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNivelVisibilidad(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NIVEL_VISIBILIDAD, newValue);
	}

	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreCompleto() throws DataStoreException {
		return getString(WEBSITE_USER_APEYNOM);
	}

	/**
	 * Retrieve the value of the website_user.nombre_completo column for the
	 * specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNombreCompleto(int row)
			throws DataStoreException {
		return getString(row, WEBSITE_USER_APEYNOM);
	}

	/**
	 * Set the value of the website_user.nombre_completo for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreCompleto(String newValue)
			throws DataStoreException {
		setString(WEBSITE_USER_APEYNOM, newValue);
	}

	/**
	 * Set the value of the website_user.nombre_completo for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNombreCompleto(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_APEYNOM, newValue);
	}

	/**
	 * Retrieve the value of the website_user.email column for the current row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserEMail() throws DataStoreException {
		return getString(WEBSITE_USER_EMAIL);
	}

	/**
	 * Retrieve the value of the website_user.email column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserEMail(int row) throws DataStoreException {
		return getString(row, WEBSITE_USER_EMAIL);
	}

	/**
	 * Set the value of the website_user.email for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserEMail(String newValue) throws DataStoreException {
		setString(WEBSITE_USER_EMAIL, newValue);
	}

	/**
	 * Set the value of the website_user.email for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserEMail(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_EMAIL, newValue);
	}


	/**
	 * Retrieve the value of the website_user.nro_legajo column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserNroLegajo() throws DataStoreException {
		return getInt(WEBSITE_USER_NRO_LEGAJO);
	}

	/**
	 * Retrieve the value of the website_user.nro_legajo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public int getWebsiteUserNroLegajo(int row) throws DataStoreException {
		return getInt(row, WEBSITE_USER_NRO_LEGAJO);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroLegajo(int newValue) throws DataStoreException {
		setInt(WEBSITE_USER_NRO_LEGAJO, newValue);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroLegajo(int row, int newValue)
			throws DataStoreException {
		setInt(row, WEBSITE_USER_NRO_LEGAJO, newValue);
	}
	
	// $CUSTOMMETHODS$
	// Put custom methods between these comments, otherwise they will be
	// overwritten if the model is regenerated
	/**
	 * Retrieve the value of the website_user.nro_legajo column for the current
	 * row.
	 * 
	 * @return int
	 * @throws DataStoreException
	 */
	public String getLegajoApellido() throws DataStoreException {
		return getString(LEGAJO_APELLIDO);
	}

	/**
	 * Retrieve the value of the website_user.nro_legajo column for the specified
	 * row.
	 * 
	 * @param row
	 *            which row in the table
	 * @return int
	 * @throws DataStoreException
	 */
	public String getLegajoApellido(int row) throws DataStoreException {
		return getString(row, LEGAJO_APELLIDO);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the current row.
	 * 
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setLegajoApellido(String newValue) throws DataStoreException {
		setString(LEGAJO_APELLIDO, newValue);
	}

	/**
	 * Set the value of the website_user.nro_legajo column for the specified row.
	 * 
	 * @param row
	 *            which row in the table
	 * @param newValue
	 *            the new item value
	 * @throws DataStoreException
	 */
	public void setLegajoApellido(int row, String newValue)
			throws DataStoreException {
		setString(row, LEGAJO_APELLIDO, newValue);
	}
	
	/**
	 * Retrieve the value of the website_user.nro_comprador column for the current
	 * row.
	 * 
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNroComprador() throws DataStoreException {
		return getString(WEBSITE_USER_NRO_COMPRADOR);
	}

	/**
	 * Retrieve the value of the website_user.nro_comprador column for the specified
	 * row.
	 * 
	 * @param row which row in the table
	 * @return String
	 * @throws DataStoreException
	 */
	public String getWebsiteUserNroComprador(int row) throws DataStoreException {
		return getString(row, WEBSITE_USER_NRO_COMPRADOR);
	}

	/**
	 * Set the value of the website_user.nro_comprador column for the current row.
	 * 
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroComprador(String newValue) throws DataStoreException {
		setString(WEBSITE_USER_NRO_COMPRADOR, newValue);
	}

	/**
	 * Set the value of the website_user.nro_comprador column for the specified row.
	 * 
	 * @param row which row in the table
	 * @param newValue the new item value
	 * @throws DataStoreException
	 */
	public void setWebsiteUserNroComprador(int row, String newValue)
			throws DataStoreException {
		setString(row, WEBSITE_USER_NRO_COMPRADOR, newValue);
	}
	// $ENDCUSTOMMETHODS$

}