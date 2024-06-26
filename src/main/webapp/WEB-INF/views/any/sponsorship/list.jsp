<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.sponsorship.label.code" path="code" width="20%"/>
	<acme:list-column code="any.sponsorship.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="any.sponsorship.label.sponsorshipType" path="sponsorshipType" sortable="false" width="20%"/>
	<acme:list-column code="any.sponsorship.label.url" path="link" sortable="false" width="20%"/>
	<acme:list-column code="any.sponsorship.label.projectCode" path="project.code" width="20%"/>
</acme:list>
