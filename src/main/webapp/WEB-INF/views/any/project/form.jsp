<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>

	<acme:input-textbox code="manager.project.label.code" path="code" />
	<acme:input-textbox code="manager.project.label.title" path="title"/>
	<acme:input-textbox code="manager.project.label.abstracts" path="abstracts"/>	
	<acme:input-money code="manager.project.label.cost" path="cost"/>
	<acme:input-url code="manager.project.label.link" path="link"/>
	
	
</acme:form>