<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.label.code" path="code" width="20%"/>
	<acme:list-column code="sponsor.invoice.label.registrationTime" path="registrationTime" width="20%"/>
	<acme:list-column code="sponsor.invoice.label.quantity" path="quantity" width="20%"/>
	<acme:list-column code="sponsor.invoice.label.tax" path="tax" width="20%"/>
	<acme:list-column code="sponsor.invoice.label.sponsorshipCode" path="sponsorship.code" width="20%"/>
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="sponsor.invoice.button.create" action="/sponsor/invoice/create?shipId=${shipId}"/>
</jstl:if>	

<!-- 
	<acme:button test="${showCreate}" code="sponsor.invoice.button.create" action="/sponsor/invoice/create?shipId=${shipId}"/>
	<jstl:if test="${_command == 'list-mine'}">
	<acme:button code="employer.job.list.button.create" action="/employer/job/create"/>
	</jstl:if>
-->