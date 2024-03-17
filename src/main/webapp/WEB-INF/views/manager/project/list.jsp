<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project.label.code" path="code" width="5%"/>
	<acme:list-column code="manager.project.label.title" path="title" width="5%"/>
	<acme:list-column code="manager.project.label.draftMode" path="draftMode" width="5%"/>
	
</acme:list>

<acme:button code="manager.project.button.create" action="/manager/project/create"/>		
