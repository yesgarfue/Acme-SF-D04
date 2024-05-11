<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="sponsor.sponsorship.label.code" path="code" placeholder="ABC-123"/>
	<acme:input-moment code="sponsor.sponsorship.label.moment" path="moment" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-textbox code="sponsor.sponsorship.label.startDate" path="startDate" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-textbox code="sponsor.sponsorship.label.finishDate" path="finishDate" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-double code="sponsor.sponsorship.label.cost" path="amount" placeholder="(EUR/USD/GBP) 00.00"/>
	<acme:input-select code="sponsor.sponsorship.label.projectCode" path="project" choices="${projects}"/>
	<acme:input-select code="sponsor.sponsorship.label.sponsorshipType" path="sponsorshipType" choices="${sponsorshipType}" readonly="${acme:anyOf(sponsorshipType,'FINANCIAL|INKIND')}"/>	
	<acme:input-textbox code="sponsor.sponsorship.label.email" path="email" placeholder="example@example.com"/>
	<acme:input-textbox code="sponsor.sponsorship.label.url" path="link" placeholder="https://www.example.com"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && isPublished == false}">
			<acme:button code="sponsor.sponsorship.button.invoices" action="/sponsor/invoice/list?shipId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isPublished == true}">
			<acme:button code="sponsor.sponsorship.button.invoices" action="/sponsor/invoice/list?shipId=${id}"/>
			<acme:submit code="sponsor.sponsorship.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.button.publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.sponsorship.button.create" action="/sponsor/sponsorship/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>