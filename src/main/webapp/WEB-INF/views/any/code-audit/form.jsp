<%--
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="any.code-audit.form.label.code" path="code"/>
	<acme:input-moment code="any.code-audit.form.label.executionDate" path="executionDate"/>
	<acme:input-textbox code="any.code-audit.form.label.type" path="type"/>	
	<acme:input-textarea code="any.code-audit.form.label.correctiveActions" path="correctiveActions"/>	
	
	<acme:button code="any.code-audit.form.button.audit-records" action="/any/audit-records/list?masterId=${id}"/>

</acme:form>