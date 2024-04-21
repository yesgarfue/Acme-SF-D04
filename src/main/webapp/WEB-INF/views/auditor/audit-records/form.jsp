<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
		<acme:input-textbox code="auditor.audit-records.form.label.code" 
		path="code" placeholder="AAAA-XXX" />
		<acme:input-moment code="auditor.audit-records.form.label.startTime" 
		path="startTime"/>
		<acme:input-moment code="auditor.audit-records.form.label.finishTime" 
		path="finishTime"/>
		<acme:input-select code="auditor.audit-records.form.label.mark" 
		path="mark" choices="${marks}"/>
		<acme:input-textarea code="auditor.audit-records.form.label.optionalLink" 
		path="optionalLink"/>
	
	<acme:hidden-data path="id"/>
	<acme:hidden-data path="draftMode"/>
	
	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="auditor.audit-records.label.draftMode"
			path="draftMode" readonly="true" />
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
		<acme:hidden-data path="draftMode"/>
		<acme:submit code="auditor.audit-records.button.create" action="/auditor/audit-records/create"/>		
	</jstl:if>
	
	<jstl:if test="${_command != 'create' && draftMode == true }">	
		<acme:submit code="auditor.audit-records.button.update" action="/auditor/audit-records/update"/>
		<acme:submit code="auditor.audit-records.button.delete" action="/auditor/audit-records/delete"/>		
		<acme:submit code="auditor.audit-records.button.publish" action="/auditor/audit-records/publish"/>		
	</jstl:if>

</acme:form>