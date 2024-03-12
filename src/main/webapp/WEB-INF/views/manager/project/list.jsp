<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.project.label.code" path="code" width="15%"/>
	<acme:list-column code="manager.project.label.title" path="title" width="15%"/>
	<acme:list-column code="manager.project.label.abstracts" path="abstracts" width="40%"/>
	<acme:list-column code="manager.project.label.indication" path="indication" width="10%"/>
	<acme:list-column code="manager.project.label.cost" path="cost" width="10%"/>
	<acme:list-column code="manager.project.label.link" path="link" width="30%"/>
	<acme:list-column code="manager.project.label.draftMode" path="draftMode" width="10%"/>
	
</acme:list>

<acme:button code="manager.project.button.create" action="/manager/project/create"/>		
