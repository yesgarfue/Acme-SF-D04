<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>

	<acme:input-textbox code="any.code-audit.label.code" path="code" />
	<acme:input-moment code="any.code-audit.label.executionDate" path="executionDate"/>
	<acme:input-textbox code="any.code-audit.label.type" path="type"/>	
	<acme:input-textbox code="any.code-audit.label.correctiveActions" path="correctiveActions"/>
	<acme:input-url code="any.code-audit.label.link" path="link"/>
	
	
</acme:form>