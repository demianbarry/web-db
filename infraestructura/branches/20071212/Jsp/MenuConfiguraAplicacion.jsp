<%@ taglib uri="/WEB-INF/taglib.tld" prefix="salmon"%>
<%@ page errorPage="ErrorPage.jsp" extends="com.salmonllc.jsp.JspServlet"%>
<salmon:page controller="infraestructura.controllers.BaseController" />
<jsp:include page="templateBefore.jsp" flush="true"></jsp:include>

<!-- *********************************************************************************************
<!-- Agregar c�digo de la p�gina aqu� -->
<table border="0" cellspacing="0" cellpadding="0" width="100%">
	<tr> 
  		<td> <table width="100%">
      		<tr> 
        		<td> <salmon:text name="text1" text="Bienvenido a la Aplicaci�n ..." textlocalekey="textWelcome" font="SectionHeadingFont"/> <BR>
					 <FONT STYLE="FONT-FAMILY:Times New Roman, Times, Verdana,Helvetica,sans-serif; FONT-SIZE:12pt;" COLOR="BLACK"><I> Aplicaci�n para Administrar ... </I> </FONT>
				</td>
      		</tr>
      		<tr> 
        		<td><br><br><salmon:text name="text2" text="Administra configuraciones de la aplicaci�n" textlocalekey="textWelcome" font="PageHeadingFont"/> <BR>
					 <FONT STYLE="FONT-FAMILY:Times New Roman, Times, Verdana,Helvetica,sans-serif; FONT-SIZE:12pt;" COLOR="BLACK"><I> Configuraci�n circuitos administrativos, acciones, estados, etc </I> </FONT><br><br>
				</td>
      		</tr>
    	</table></td>
	</tr>
	<tr>
  		<td><%@include file="message.jsp"%> </td>
	</tr>
</table>
<jsp:include page="templateAfter.jsp" flush="true"></jsp:include>
<salmon:endpage/>