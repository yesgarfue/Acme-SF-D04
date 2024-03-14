<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>

	<acme:input-integer code="administrator.dashboard.label.numberOfPrincipalsByRol" path="numberOfPrincipalsByRol" placeholder=""/>
	<acme:input-integer code="administrator.dashboard.label.noticeWithEmailAndLinkRatio" path="noticeWithEmailAndLinkRatio" placeholder=""/>
	<acme:input-integer code="administrator.dashboard.label.criticalObjectiveRatio" path="criticalObjectiveRatio" placeholder=""/>
	<acme:input-integer code="administrator.dashboard.label.nonCriticalObjectiveRatio" path="nonCriticalObjectiveRatio" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.averageValueRisk" path="averageValueRisk" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.minimunValueRisk" path="minimunValueRisk" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.maximunValueRisk" path="maximunValueRisk" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.desviationValueRisk" path="desviationValueRisk" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.averageNumberOfClaimsOverLastTenWeeks" path="averageNumberOfClaimsOverLastTenWeeks" placeholder=""/>
	<acme:input-integer code="administrator.dashboard.label.minimunNumberOfClaimsOverLastTenWeeks" path="minimunNumberOfClaimsOverLastTenWeeks" placeholder=""/>
	<acme:input-integer code="administrator.dashboard.label.maximunNumberOfClaimsOverLastTenWeeks" path="maximunNumberOfClaimsOverLastTenWeeks" placeholder=""/>
	<acme:input-double code="administrator.dashboard.label.desviationNumberOfClaimsOverLastTenWeeks" path="desviationNumberOfClaimsOverLastTenWeeks" placeholder=""/>
	
</acme:form>