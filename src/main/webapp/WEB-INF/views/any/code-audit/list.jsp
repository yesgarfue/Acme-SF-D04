<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

		
<acme:list>
	<acme:list-column code="any.code-audit.label.code" path="code" width="5%"/>
	<acme:list-column code="any.code-audit.label.type" path="type" width="5%"/>
	<acme:list-column code="any.code-audit.label.correctiveActions" path="correctiveActions" width="5%"/>
</acme:list>