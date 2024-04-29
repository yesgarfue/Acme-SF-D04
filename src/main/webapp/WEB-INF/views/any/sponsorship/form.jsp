<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form readonly="true">
	<acme:input-textbox code="any.sponsorship.label.code" path="code"/>
	<acme:input-moment code="any.sponsorship.label.moment" path="moment"/>
	<acme:input-textbox code="any.sponsorship.label.startDate" path="startDate"/>
	<acme:input-textbox code="any.sponsorship.label.finishDate" path="finishDate"/>
	<acme:input-double code="any.sponsorship.label.cost" path="amount"/>
	<acme:input-textbox code="any.sponsorship.label.projectCode" path="project"/>
	<acme:input-textbox code="any.sponsorship.label.sponsorshipType" path="sponsorshipType"/>	
	<acme:input-textbox code="any.sponsorship.label.email" path="email"/>
	<acme:input-textbox code="any.sponsorship.label.url" path="link"/>
</acme:form>