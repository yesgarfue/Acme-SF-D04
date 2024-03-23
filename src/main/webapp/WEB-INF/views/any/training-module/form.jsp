<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="developer.training-module.form.label.code"
		path="code" />
	<acme:input-textbox
		code="developer.training-module.form.label.project"
		path="project"  />
	<acme:input-moment
		code="developer.training-module.form.label.creationMoment"
		path="creationMoment" />
	<acme:input-textbox code="developer.training-module.form.label.details"
		path="details" />
	<acme:input-textbox
		code="developer.training-module.form.label.difficultyLevel"
		path="difficultyLevel" />
	<acme:input-moment
		code="developer.training-module.form.label.updatedMoment"
		path="updatedMoment" />
	<acme:input-url
		code="developer.training-module.form.label.optionalLink"
		path="optionalLink" />
	<acme:input-textbox
		code="developer.training-module.form.label.totalTime" path="totalTime" />

</acme:form>
