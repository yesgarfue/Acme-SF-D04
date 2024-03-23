<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="developer.trainingSession.label.code" path="code" width="15%"/>
	<acme:list-column code="developer.trainingSession.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="developer.trainingSession.label.endDate" path="endDate" width="25%"/>
	<acme:list-column code="developer.trainingSession.label.location" path="location" width="10%"/>
	<acme:list-column code="developer.trainingSession.label.instructor" path="instructor" width="20%"/>
	<acme:list-column code="developer.trainingSession.label.trainingModule" path="trainingModule" width="10%"/>
	
</acme:list>

<acme:button code="developer.trainingSession.button.create" action="/developer/training-session/create"/>