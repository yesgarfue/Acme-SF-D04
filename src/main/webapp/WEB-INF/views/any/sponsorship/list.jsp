<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="Code" path="code" width="10%"/>
	<acme:list-column code="StartDate" path="startDate" width="20%"/>
	<acme:list-column code="Cost" path="amount" sortable="false" width="20%"/>
	<acme:list-column code="TypeSponsorship" path="sponsorshipType" sortable="false" width="20%"/>
	<acme:list-column code="Email" path="email" sortable="false" width="10%"/>
	<acme:list-column code="URL" path="link" sortable="false" width="20%"/>
</acme:list>