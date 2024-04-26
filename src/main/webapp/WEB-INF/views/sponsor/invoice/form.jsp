<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
	<acme:input-textbox code="sponsor.invoice.label.code" path="code"/>
	<acme:input-moment code="sponsor.invoice.label.registrationTime" path="registrationTime"/>
	<acme:input-moment code="sponsor.invoice.label.dueDate" path="dueDate"/>
	<acme:input-money code="sponsor.invoice.label.quantity" path="quantity"/>
	<acme:input-double code="sponsor.invoice.label.tax" path="tax"/>
	<acme:input-textbox code="sponsor.invoice.label.link" path="link"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish')}">
			<acme:submit code="sponsor.invoice.button.update" action="/sponsor/invoice/update"/>
			<acme:submit code="sponsor.invoice.button.delete" action="/sponsor/invoice/delete"/>
			<acme:submit code="sponsor.invoice.button.publish" action="/sponsor/invoice/publish"/>
		</jstl:when>
		<jstl:when test="${_command =='create'}">
			<acme:submit code="sponsor.invoice.list.button.create" action="/sponsor/invoice/create?shipId=${shipId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
