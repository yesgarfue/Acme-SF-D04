<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:hidden-data path="id"/>
	<acme:input-textbox code="sponsor.sponsorship.label.code" path="code"/>
	<acme:input-moment code="sponsor.sponsorship.label.moment" path="moment"/>
	<acme:input-textbox code="sponsor.sponsorship.label.startDate" path="startDate"/>
	<acme:input-textbox code="sponsor.sponsorship.label.finishDate" path="finishDate"/>
	<acme:input-double code="sponsor.sponsorship.label.cost" path="amount"/>
	<acme:input-select code="sponsor.sponsorship.label.projectCode" path="project" choices="${projects}"/>
	<acme:input-select code="sponsor.sponsorship.label.sponsorshipType" path="sponsorshipType" choices="${sponsorshipType}" readonly="${acme:anyOf(sponsorshipType,'FINANCIAL|INKIND')}"/>	
	<acme:input-textbox code="sponsor.sponsorship.label.email" path="email"/>
	<acme:input-textbox code="sponsor.sponsorship.label.url" path="link"/>
	<jstl:choose>
		<jstl:when test="${_command =='show'}">
			<acme:button code="sponsor.sponsorship.button.invoices" action="/sponsor/invoice/list?shipId=${id}"/>
			<acme:submit code="sponsor.sponsorship.button.update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="sponsor.sponsorship.button.delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="sponsor.sponsorship.button.publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>