<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>



<acme:form>
	<acme:input-integer code="manager.dashboard.label.numberMust" path="numberMust" placeholder=""/>
	<acme:input-integer code="manager.dashboard.label.numberShould" path="numberShould" placeholder=""/>
	<acme:input-integer code="manager.dashboard.label.numberCould" path="numberCould" placeholder=""/>
	<acme:input-integer code="manager.dashboard.label.numberWont" path="numberWont" placeholder=""/>
		
	
	<acme:input-double code="manager.dashboard.label.averageEstimate" path="averageEstimate" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.deviationEstimate" path="deviationEstimate" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.minimumEstimate" path="minimumEstimate" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.maximumEstimate" path="maximumEstimate" placeholder=""/>
	
	<acme:input-double code="manager.dashboard.label.averageCost" path="averageCost" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.deviationCost" path="deviationCost" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.minimumCost" path="minimumCost" placeholder=""/>
	<acme:input-double code="manager.dashboard.label.maximumCost" path="maximumCost" placeholder=""/>
</acme:form>