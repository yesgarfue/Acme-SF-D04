<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-double code="sponsor.dashboard.label.sponsorship.minimumSponsorshipsAmount" path="minimumSponsorshipsAmount" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.sponsorship.maximunSponsorshipsAmount" path="maximunSponsorshipsAmount" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.sponsorship.averageSponsorshipsAmount" path="averageSponsorshipsAmount" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.sponsorship.deviationSponsorshipsAmount" path="deviationSponsorshipsAmount" placeholder=""/>
	
	<acme:input-double code="sponsor.dashboard.label.invoice.minimumInvoicesQuantity" path="minimumInvoicesQuantity" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.invoice.maximunInvoicesQuantity" path="maximunInvoicesQuantity" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.invoice.averageInvoicesQuantity" path="averageInvoicesQuantity" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.invoice.deviationInvoicesQuantity" path="deviationInvoicesQuantity" placeholder=""/>
	
	<acme:input-double code="sponsor.dashboard.label.totalNumInvoicesWithTaxLessOrEqualTo21" path="totalNumInvoicesWithTaxLessOrEqualTo21" placeholder=""/>
	<acme:input-double code="sponsor.dashboard.label.totalNumInvoicesWithLink" path="totalNumInvoicesWithLink" placeholder=""/>
</acme:form>