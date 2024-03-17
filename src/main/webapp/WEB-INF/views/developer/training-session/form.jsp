<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-session.form.label.code"
		path="code" />
	<acme:input-select
		code="developer.training-session.form.label.trainingModule"
		path="trainingModule" choices="${trainingModules}" />
	<acme:input-moment
		code="developer.training-session.form.label.startDate"
		path="startDate" />
	<acme:input-moment
		code="developer.training-session.form.label.endDate"
		path="endDate" />
	<acme:input-textbox code="developer.training-session.form.label.location"
		path="location" />
	<acme:input-textbox
		code="developer.training-session.form.label.instructor"
		path="instructor"/>
	<acme:input-email
		code="developer.training-session.form.label.contactEmail"
		path="contactEmail" />
	<acme:input-url
		code="developer.training-session.form.label.optionalLink"
		path="optionalLink" />


	<acme:hidden-data path="id" />
	<acme:hidden-data path="draftMode"/>

	<jstl:if test="${_command != 'create'}">
		<acme:input-checkbox code="developer.training-session.label.draftMode"
			path="draftMode" readonly="true" />

	</jstl:if>
	<jstl:if test="${_command == 'create'}">
		<acme:hidden-data path="draftMode" />

		<acme:submit code="developer.training-session.button.create"
			action="/developer/training-session/create" />
	</jstl:if>


	<jstl:if test="${_command != 'create' && draftMode == true }">
		<acme:submit code="developer.training-session.button.update"
			action="/developer/training-session/update" />
		<acme:submit code="developer.training-session.button.delete"
			action="/developer/training-session/delete" />
		<acme:submit code="developer.training-session.button.publish"
			action="/developer/training-session/publish" />
	</jstl:if>
</acme:form>