<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>



<acme:form>
	<acme:input-integer code="auditor.dashboard.label.totalNumberOfStaticCodeAudits" path="totalNumberOfStaticCodeAudits" placeholder=""/>
	<acme:input-integer code="auditor.dashboard.label.totalNumberOfDynamicCodeAudits" path="totalNumberOfDynamicCodeAudits" placeholder=""/>
		
	<acme:input-double code="auditor.dashboard.label.averageNumberOfAuditingRecords" path="averageNumberOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.deviationOfAuditingRecords" path="deviationOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.minimumNumberOfAuditingRecords" path="minimumNumberOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.maximumNumberOfAuditingRecords" path="maximumNumberOfAuditingRecords" placeholder=""/>
	
	<acme:input-double code="auditor.dashboard.label.averageTimeOfAuditingRecords" path="averageTimeOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.timeDeviationOfAuditingRecords" path="timeDeviationOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.minimumTimeOfAuditingRecords" path="minimumTimeOfAuditingRecords" placeholder=""/>
	<acme:input-double code="auditor.dashboard.label.maximumTimeOfAuditingRecords" path="maximumTimeOfAuditingRecords" placeholder=""/>
</acme:form>