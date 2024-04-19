<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:hidden-data path="id"/>
	
	<jstl:if test="${_command == 'show'}">
		<acme:input-textbox code="auditor.code-audit.label.code" path="code" placeholder="AAA-XXX" />
		<acme:input-moment code="auditor.code-audit.label.executionDate" path="executionDate"/>
		<acme:input-textbox code="auditor.code-audit.label.type" path="type"/>
		<acme:input-textbox code="auditor.code-audit.form.label.project" path="project"/>
		<acme:input-textarea code="auditor.code-audit.label.correctiveActions" path="correctiveActions"/>
	</jstl:if>
	
	<jstl:if test="${_command == 'create'}">
		<acme:hidden-data path="draftMode"/>
		<acme:input-textbox code="auditor.code-audit.label.code" path="code" placeholder="AAA-XXX" />
		<acme:input-moment code="auditor.code-audit.label.executionDate" path="executionDate"/>
		<acme:input-select code="auditor.code-audit.label.type" path="type" choices="${types}"/>
		<acme:input-select code="auditor.code-audit.form.label.project" path="project" choices="${projects}" />
		<acme:input-textarea code="auditor.code-audit.label.correctiveActions" path="correctiveActions"/>
		<acme:submit code="auditor.code-audit.button.create" action="/auditor/code-audit/create"/>		
	</jstl:if>

</acme:form>