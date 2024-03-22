<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>


<acme:form>

	<acme:input-textbox code="administrator.dashboard.label.numberOfPrincipalsByRol" path="roles"/>
	
	<acme:input-integer code="administrator.dashboard.label.noticeWithEmailAndLinkRatio" path="noticeWithEmailAndLinkRatio" />
	<acme:input-integer code="administrator.dashboard.label.criticalObjectiveRatio" path="criticalObjectiveRatio" />
	<acme:input-integer code="administrator.dashboard.label.nonCriticalObjectiveRatio" path="nonCriticalObjectiveRatio" />
	<acme:input-double code="administrator.dashboard.label.averageValueRisk" path="averageValueRisk" />
	<acme:input-double code="administrator.dashboard.label.minimunValueRisk" path="minimunValueRisk" />
	<acme:input-double code="administrator.dashboard.label.maximunValueRisk" path="maximunValueRisk" />
	<acme:input-double code="administrator.dashboard.label.desviationValueRisk" path="desviationValueRisk" />
	<acme:input-double code="administrator.dashboard.label.averageNumberOfClaimsOverLastTenWeeks" path="averageNumberOfClaimsOverLastTenWeeks" />
	<acme:input-integer code="administrator.dashboard.label.minimunNumberOfClaimsOverLastTenWeeks" path="minimunNumberOfClaimsOverLastTenWeeks" />
	<acme:input-integer code="administrator.dashboard.label.maximunNumberOfClaimsOverLastTenWeeks" path="maximunNumberOfClaimsOverLastTenWeeks"/>
	<acme:input-double code="administrator.dashboard.label.desviationNumberOfClaimsOverLastTenWeeks" path="desviationNumberOfClaimsOverLastTenWeeks"/>
	
</acme:form>