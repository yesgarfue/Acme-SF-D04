<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>

	<acme:input-textbox code="manager.project.label.code" path="code"/>
	<acme:input-textbox code="manager.project.label.title" path="title"/>
	<acme:input-textarea code="manager.project.label.abstracts" path="abstracts"/>	
	<acme:input-money code="manager.project.label.cost" path="cost"/>
	<acme:input-checkbox code="manager.project.label.indication" path="indication"/>
	<acme:input-url code="manager.project.label.link" path="link"/>
	
	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="manager.project.label.draftMode" path="draftMode" readonly="true"/>
		
		<acme:button code="manager.project.button.userStoryList" action="/manager/userStory/list?projectId=${id}"/>
	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:hidden-data path="draftMode"/>
		
		<acme:submit code="manager.project.button.create" action="/manager/project/create"/>		
	</jstl:if>
	
	
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="manager.project.button.update" action="/manager/project/update"/>
		<acme:submit code="manager.project.button.delete" action="/manager/project/delete"/>		
		<acme:submit code="manager.project.button.publish" action="/manager/project/publish"/>		
	</jstl:if>
	
</acme:form>