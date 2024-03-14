<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.user-story.label.title" path="title" width="5%"/>
	<acme:list-column code="manager.user-story.label.description" path="description" width="5%"/>
	<acme:list-column code="manager.user-story.label.draftMode" path="draftMode" width="5%"/>
</acme:list>
<jstl:if test="${projectId != null && draftMode == true}">
	<acme:button code="manager.user-story.button.add" action="/manager/project-user-story/add?projectId=${projectId}"/>		
	<acme:button code="manager.user-story.button.create" action="/manager/user-story/create?projectId=${projectId}"/>
</jstl:if>
<jstl:if test="${projectId == null}">
	<acme:button code="manager.user-story.button.create" action="/manager/user-story/create"/>
</jstl:if>