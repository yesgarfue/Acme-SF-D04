<%@ page language="java"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:input-textbox code="developer.training-module.form.label.code"
        path="code" />
    
    <acme:input-select
        code="developer.training-module.form.label.project"
        path="project" choices="${projects}" />
    
    <acme:input-moment
        code="developer.training-module.form.label.creationMoment"
        path="creationMoment" />
    
    <acme:input-textbox code="developer.training-module.form.label.details"
        path="details" />
    
    <acme:input-select
        code="developer.training-module.form.label.difficultyLevel"
        path="difficultyLevel" choices="${difficultyLevels}" />
    
    <acme:input-moment
        code="developer.training-module.form.label.updatedMoment"
        path="updatedMoment" />
    
    <acme:input-url
        code="developer.training-module.form.label.optionalLink"
        path="optionalLink" />
    
    <acme:input-textarea
        code="developer.training-module.form.label.totalTime" 
        path="totalTime" />

    <acme:hidden-data path="id" />
    <acme:hidden-data path="draftMode" />

    <jstl:if test="${_command != 'create'}">
        <acme:input-checkbox code="developer.training-module.label.draftMode"
            path="draftMode" readonly="true" />
    </jstl:if>

    <jstl:if test="${_command == 'create'}">
        <acme:hidden-data path="draftMode" />
        <acme:submit code="developer.training-module.button.create"
            action="/developer/training-module/create" />
    </jstl:if>

    <jstl:if test="${_command != 'create' && draftMode == true}">
        <acme:submit code="developer.training-module.button.update"
            action="/developer/training-module/update" />
        <acme:submit code="developer.training-module.button.delete"
            action="/developer/training-module/delete" />
        <acme:submit code="developer.training-module.button.publish"
            action="/developer/training-module/publish" />
    </jstl:if>
</acme:form>
