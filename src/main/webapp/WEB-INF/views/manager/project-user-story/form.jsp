<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>

	<acme:hidden-data path="id"/>
	<acme:input-textbox code="manager.project-user-story.label.project" path="projectCode" readonly="true"/>
	<acme:input-select code="manager.project-user-story.label.user-story" path="user-story" choices="${userStories}"/>
	
	<acme:submit code="manager.project-user-story.button.add" action="/manager/project-user-story/add?projectId=${projectId}"/>		
	
</acme:form>