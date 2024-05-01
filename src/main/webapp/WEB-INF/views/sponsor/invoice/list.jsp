<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.invoice.label.code" path="code"/>
	<acme:list-column code="sponsor.invoice.label.registrationTime" path="registrationTime"/>
	<acme:list-column code="sponsor.invoice.label.dueDate" path="dueDate"/>
	<acme:list-column code="sponsor.invoice.label.quantity" path="quantity"/>
	<acme:list-column code="sponsor.invoice.label.tax" path="tax"/>
	<acme:list-column code="sponsor.invoice.label.link" path="link"/>
</acme:list>

<acme:button test="${showCreate}" code="sponsor.invoice.list.button.create" action="/sponsor/invoice/create?shipId=${shipId}"/>

