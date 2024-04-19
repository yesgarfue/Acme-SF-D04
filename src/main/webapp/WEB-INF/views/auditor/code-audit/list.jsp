<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.code-audit.label.code" path="code" width="15%"/>
	<acme:list-column code="auditor.code-audit.label.type" path="type" width="20%"/>
	<acme:list-column code="auditor.code-audit.label.correctiveActions" path="correctiveActions" width="25%"/>
</acme:list>

<acme:button code="auditor.code-audit.button.create" action="/auditor/code-audit/create"/>		


	
