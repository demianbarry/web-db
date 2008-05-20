<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp"
	extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>
<salmon:form name="PageForm">
	<%@include file="message.jsp"%>
	<!-- ********************************************************************************************* -->
	<!-- Agregar definici�n de DataSource aqu� -->
	<salmon:datasource name="dsQBE" type="QBE">
		<salmon:qbecriteria name="articulo" type="CONTAINS"
			columns="resumen_saldo_articulos.articulo_id,articulos.nombre,articulos.descripcion" />
		<salmon:qbecriteria name="almacen" type="IN"
			columns="resumen_saldo_articulos.almacen_id" />
		<salmon:qbecriteria name="periodo" type="IN"
			columns="resumen_saldo_articulos.periodo" />
	</salmon:datasource>
	<salmon:datasource name="dsResumenes" type="MODEL"
		dbprofile="inventario"
		model="inventario.models.ResumenSaldoArticulosModel"
		autoretrieve="Never">
	</salmon:datasource>
	<!-- ********************************************************************************************* -->
	<!-- Agregar c�digo de la p�gina aqu� -->
	<!-- ********************************************************************************************* -->
	<salmon:box name="box1" width="100%">
		<salmon:table name="table2" width="100%" border="0">
			<salmon:tr>
				<salmon:td valign="Top">
					<salmon:searchformdisplaybox
						caption="Consulta de Resumenes de Stock de Art�culos"
						name="searchformdisplaybox1" buttondisplaylocation="BELOW_TABLE"
						searchbuttonvisible="true" addbuttonvisible="False"
						qbebuilder="dsQBE" width="100%">
						<table width="100%">
							<tr>
								<td><salmon:text name="articulo1" text="Art�culo"
									font="TableHeadingFont" />
								<td><salmon:lookup browseimage="%ImageDirectory/Browse.gif"
									lookupurl="%LkpArticulos" name="articulo2" size="40"
									maxlength="15" datasource="dsQBE:articulo" popupheight="450"
									popupwidth="600" usepopup="true" showdescription="true"></salmon:lookup></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen1" text="Almac�n"
									font="TableHeadingFont" /></td>
								<salmon:td>
									<salmon:input type="select" name="almacen2" size="30"
										datasource="dsQBE:almacen">
										<salmon:option display="abc" key="123"
											table="inventario.almacenes" keycolumn="almacen_id"
											displaycolumn="nombre" nulloption="true"
											nulloptiontext="Todos"></salmon:option>
									</salmon:input>
								</salmon:td>
							</tr>
							<tr>
								<td><salmon:text name="periodo1" text="Per�odo"
									font="TableHeadingFont" /></td>
								<td><salmon:input type="select" name="periodo2" size="10"
									datasource="dsQBE:periodo" displayformat="MM-yyyy"
									maxlength="10">
									<salmon:option display="abc" key="123"
										table="inventario.periodo_en_stock" keycolumn="periodo"
										displaycolumn="periodo_formateado" nulloption="true"
										nulloptiontext="Todos"></salmon:option>
								</salmon:input></td>
							</tr>
						</table>
					</salmon:searchformdisplaybox>
				</salmon:td>
				<salmon:td valign="Top">
					<salmon:detailformdisplaybox name="detailformdisplaybox1"
						addbuttonvisible="false" cancelbuttonvisible="false"
						savebuttonvisible="false" deletebuttonvisible="false"
						caption="Detalle del stock" width="100%" datasource="dsResumenes"
						listformdisplaybox="listformdisplaybox1">
						<table width="100%">
							<tr>
								<td><salmon:text name="articulo3" text="Art�culo"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="articulo4" text=""
									datasource="dsResumenes:articulos.nombre +' - '+ articulos.descripcion + articulos.descripcion_completa"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="almacen3" text="Almacen"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="almacen4" text=""
									datasource="dsResumenes:almacenes.nombre"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="periodo3" text="Periodo"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="periodo4" text=""
									datasource="dsResumenes:resumen_saldo_articulos.periodo"
									displayformat="MM-yyyy"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="stock_en_mano1" text="Stock"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="stock_en_mano2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.stock_en_mano"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="reservado1" text="Reservado"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="reservado2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.reservado"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="en_proceso1" text="En proceso"
									font="TableHeadingFont" /></td>
								<td><salmon:text name="en_proceso2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.en_proceso"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="total_ingresos1"
									text="Total de ingresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="total_ingresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.total_ingresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="total_egresos1"
									text="Total de egresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="total_egresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.total_egresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cant_transacciones_ingresos1"
									text="Transacciones de ingresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="cant_transacciones_ingresos2"
									text=""
									datasource="dsResumenes:resumen_saldo_articulos.cant_transacciones_ingresos"></salmon:text></td>
							</tr>
							<tr>
								<td><salmon:text name="cant_transacciones_egresos1"
									text="Transacciones de egresos" font="TableHeadingFont" /></td>
								<td><salmon:text name="cant_transacciones_egresos2" text=""
									datasource="dsResumenes:resumen_saldo_articulos.cant_transacciones_egresos"></salmon:text></td>
							</tr>
						</table>
					</salmon:detailformdisplaybox>
				</salmon:td>
			</salmon:tr>
		</salmon:table>
	</salmon:box>
	<salmon:box name="box2" width="100%">
		<salmon:listformdisplaybox name="listformdisplaybox1"
			mode="Display_single_page" caption="Resumen de stock de art�culos"
			width="100%" datasource="dsResumenes"
			searchformdisplaybox="searchformdisplaybox1">
			<salmon:datatable name="datatable1" width="100%" rowsperpage="5"
				datasource="dsResumenes">
				<salmon:datatableheader>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo5" text="Art�culo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="almacen5" text="Almacen"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="periodo5" text="Periodo"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="stock_en_mano3" text="Stock"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservado3" text="Reservado"
								font="TableHeadingFont" />
						</salmon:td>
						<salmon:td>
							<salmon:text name="en_proceso3" text="En proceso"
								font="TableHeadingFont" />
						</salmon:td>
					</salmon:tr>
				</salmon:datatableheader>
				<salmon:datatablerows>
					<salmon:tr>
						<salmon:td>
							<salmon:text name="articulo6" text=""
								datasource="dsResumenes:articulos.nombre +' - '+ articulos.descripcion + articulos.descripcion_completa"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="almacen6" text=""
								datasource="dsResumenes:almacenes.nombre"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="periodo6" text=""
								datasource="dsResumenes:resumen_saldo_articulos.periodo"
								displayformat="MM-yyyy"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="stock_en_mano4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.stock_en_mano"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="reservado4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.reservado"></salmon:text>
						</salmon:td>
						<salmon:td>
							<salmon:text name="en_proceso4" text=""
								datasource="dsResumenes:resumen_saldo_articulos.en_proceso"></salmon:text>
						</salmon:td>
					</salmon:tr>
				</salmon:datatablerows>
			</salmon:datatable>
		</salmon:listformdisplaybox>
	</salmon:box>
	<!-- Fin de c�digo agregado -->
</salmon:form>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage />