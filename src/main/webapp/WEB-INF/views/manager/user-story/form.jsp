<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	
	<jstl:if test="${_command == 'create' && projectCode!=null}">
		<acme:input-textbox code="manager.user-story.label.projectCode" path="projectCode" readonly="true"/>
	</jstl:if>	
	<acme:input-textbox code="manager.user-story.label.title" path="title"/>
	<acme:input-textarea code="manager.user-story.label.description" path="description"/>	
	<acme:input-money code="manager.user-story.label.estimatedCost" path="estimatedCost"/>
	<acme:input-textarea code="manager.user-story.label.acceptanceCriteria" path="acceptanceCriteria"/>
	<acme:input-textbox code="manager.user-story.label.link" path="link"/>
	<jstl:if test="${draftMode == false}">
	<acme:input-textbox code="manager.user-story.label.priority" path="priority" readonly="true"/>
	</jstl:if>
	<jstl:if test="${draftMode != false}">
	<acme:input-select code="manager.user-story.label.priority" path="priority" choices="${priorities}"/>
	</jstl:if>
	<acme:input-checkbox code="manager.user-story.label.draftMode" path="draftMode" readonly="true"/>

	<acme:submit test="${_command == 'create' && projectCode!=null}" code="manager.user-story.button.create" action="/manager/user-story/create?projectId=${projectId}"/>		
	<acme:submit test="${_command == 'create' && projectCode==null}" code="manager.user-story.button.create" action="/manager/user-story/create"/>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="manager.user-story.button.update" action="/manager/user-story/update"/>
		<acme:submit code="manager.user-story.button.delete" action="/manager/user-story/delete"/>		
		<acme:submit code="manager.user-story.button.publish" action="/manager/user-story/publish"/>		
	</jstl:if>
	
</acme:form>