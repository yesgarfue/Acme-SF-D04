<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:hidden-data path="id"/>
	<acme:input-textbox code="Code" path="code"/>
	<acme:input-moment code="Moment" path="moment"/>
	<acme:input-textbox code="StartDate" path="startDate"/>
	<acme:input-textbox code="FinishDate" path="finishDate"/>
	<acme:input-double code="Cost" path="amount"/>
	<acme:input-textbox code="Email" path="email"/>
	<acme:input-textbox code="URL" path="link"/>
	<jstl:choose>
		<jstl:when test="${_command =='show'}">
			<acme:submit code="Create" action="/sponsor/sponsorship/create"/>
			<acme:submit code="Update" action="/sponsor/sponsorship/update"/>
			<acme:submit code="Delete" action="/sponsor/sponsorship/delete"/>
			<acme:submit code="Publish" action="/sponsor/sponsorship/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>