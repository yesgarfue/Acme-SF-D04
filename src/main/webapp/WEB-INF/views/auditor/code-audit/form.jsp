<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.code-audit.form.label.code" 
		path="code" placeholder="AAA-XXX" />
		<acme:input-moment code="auditor.code-audit.form.label.executionDate" 
		path="executionDate"/>
		<acme:input-select code="auditor.code-audit.form.label.type" 
		path="type" choices="${types}"/>
		<acme:input-select code="auditor.code-audit.form.label.project" 
		path="project" choices="${projects}"/>
		<acme:input-textarea code="auditor.code-audit.form.label.correctiveActions" 
		path="correctiveActions"/>
	
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="draftMode"/>
	
	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="auditor.code-audit.label.draftMode"
			path="draftMode" readonly="true" />
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
		<acme:hidden-data path="draftMode"/>
		<acme:submit code="auditor.code-audit.button.create" action="/auditor/code-audit/create"/>		
	</jstl:if>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="auditor.code-audit.button.update" action="/auditor/code-audit/update"/>
		<acme:submit code="auditor.code-audit.button.delete" action="/auditor/code-audit/delete"/>		
		<acme:submit code="auditor.code-audit.button.publish" action="/auditor/code-audit/publish"/>		
	</jstl:if>

</acme:form>