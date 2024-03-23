<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingModule.label.code" path="code" width="15%"/>
	<acme:list-column code="developer.trainingModule.label.creationMoment" path="creationMoment" width="20%"/>
	<acme:list-column code="developer.trainingModule.label.details" path="details" width="25%"/>
	<acme:list-column code="developer.trainingModule.label.difficultyLevel" path="difficultyLevel" width="10%"/>
	<acme:list-column code="developer.trainingModule.label.totalTime" path="totalTime" width="10%"/>
	<acme:list-column code="developer.trainingModule.label.project" path="project" width="10%"/>
	
</acme:list>

<acme:button code="developer.trainingModule.button.create" action="/developer/training-module/create"/>