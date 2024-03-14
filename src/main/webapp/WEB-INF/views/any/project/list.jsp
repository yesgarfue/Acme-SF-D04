<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

		
<acme:list>
	<acme:list-column code="manager.project.label.code" path="code" width="5%"/>
	<acme:list-column code="manager.project.label.title" path="title" width="5%"/>
	<acme:list-column code="manager.project.label.cost" path="cost" width="5%"/>
</acme:list>