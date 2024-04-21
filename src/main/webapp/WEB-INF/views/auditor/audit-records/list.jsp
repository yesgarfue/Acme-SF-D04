<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.audit-records.label.code" path="code" width="15%"/>
	<acme:list-column code="auditor.audit-records.label.mark" path="mark" width="20%"/>
	<acme:list-column code="auditor.audit-records.label.optionalLink" path="optionalLink" width="25%"/>
</acme:list>

<acme:button code="auditor.audit-records.button.create" action="/auditor/audit-records/create"/>		


	
