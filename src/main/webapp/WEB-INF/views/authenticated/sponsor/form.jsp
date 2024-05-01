<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:input-textbox code="authenticated.sponsor.form.label.name" path="name" placeholder="maximum of 75 characters"/>
	<acme:input-textbox code="authenticated.sponsor.form.label.expectedBenefits" path="expectedBenefits" placeholder="maximum of 100 characters"/>
	<acme:input-textbox code="authenticated.sponsor.form.label.webPage" path="webPage" placeholder="http://example.com"/>
	<acme:input-textbox code="authenticated.sponsor.form.label.emailContact" path="emailContact" placeholder="example@example.com"/>
	
	<acme:submit test="${_command == 'create'}" code="authenticated.sponsor.form.button.create" action="/authenticated/sponsor/create"/>
	<acme:submit test="${_command == 'update'}" code="authenticated.sponsor.form.button.update" action="/authenticated/sponsor/update"/>
	
</acme:form>