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
	
</acme:form>

