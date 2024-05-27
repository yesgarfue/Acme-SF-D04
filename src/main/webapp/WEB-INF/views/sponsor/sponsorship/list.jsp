<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.label.sponsorshipType" path="sponsorshipType" sortable="true" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.label.sponsorshipAmount" path="amount" sortable="true" width="20%"/>
	<acme:list-column code="sponsor.sponsorship.label.projectId" path="project.code" width="20%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button code="sponsor.sponsorship.button.create" action="/sponsor/sponsorship/create"/>

