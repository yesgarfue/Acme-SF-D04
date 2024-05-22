<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
 
<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.totalNumInvoicesWithTaxLessOrEqualTo21"/></th>
		<td><acme:print value="${totalNumInvoicesWithTaxLessOrEqualTo21}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.totalNumInvoicesWithLink"/></th>
		<td><acme:print value="${totalNumInvoicesWithLink}"/></td>
	</tr>
</table>

<acme:message code="sponsor.dashboard.label.euros"/>
<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.minimumSponsorshipsAmount"/></th>
		<td><acme:print value="${minimumSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.minimumInvoicesQuantity"/></th>
		<td><acme:print value="${minimumInvoicesQuantity}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.maximunSponsorshipsAmount"/></th>
		<td><acme:print value="${maximunSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.maximunInvoicesQuantity"/></th>
		<td><acme:print value="${maximunInvoicesQuantity}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.averageSponsorshipsAmount"/></th>
		<td><acme:print value="${averageSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.averageInvoicesQuantity"/></th>
		<td><acme:print value="${averageInvoicesQuantity}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.deviationSponsorshipsAmount"/></th>
		<td><acme:print value="${deviationSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.deviationInvoicesQuantity"/></th>
		<td><acme:print value="${deviationInvoicesQuantity}"/></td>
	</tr>
</table>

<acme:message code="sponsor.dashboard.label.usd"/>
<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.minimumSponsorshipsAmount"/></th>
		<td><acme:print value="${usdMinimumSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.minimumInvoicesQuantity"/></th>
		<td><acme:print value="${usdMinimumSponsorshipsAmount}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.maximunSponsorshipsAmount"/></th>
		<td><acme:print value="${usdMaximunSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.maximunInvoicesQuantity"/></th>
		<td><acme:print value="${usdMaximunInvoicesQuantity}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.averageSponsorshipsAmount"/></th>
		<td><acme:print value="${usdAverageSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.averageInvoicesQuantity"/></th>
		<td><acme:print value="${usdAverageInvoicesQuantityt}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.deviationSponsorshipsAmount"/></th>
		<td><acme:print value="${usdDeviationSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.deviationInvoicesQuantity"/></th>
		<td><acme:print value="${usdDeviationInvoicesQuantity}"/></td>
	</tr>
</table>

<acme:message code="sponsor.dashboard.label.gbp"/>
<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.minimumSponsorshipsAmount"/></th>
		<td><acme:print value="${gbpMinimumSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.minimumInvoicesQuantity"/></th>
		<td><acme:print value="${gbpMinimumSponsorshipsAmount}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.maximunSponsorshipsAmount"/></th>
		<td><acme:print value="${gbpMaximunSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.maximunInvoicesQuantity"/></th>
		<td><acme:print value="${gbpMaximunInvoicesQuantity}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.averageSponsorshipsAmount"/></th>
		<td><acme:print value="${gbpAverageSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.averageInvoicesQuantity"/></th>
		<td><acme:print value="${gbpAverageInvoicesQuantityt}"/></td>
	</tr>
	<tr>
		<th scope="row"><acme:message code="sponsor.dashboard.label.sponsorship.deviationSponsorshipsAmount"/></th>
		<td><acme:print value="${gbpDeviationSponsorshipsAmount}"/></td>
		
		<th scope="row"><acme:message code="sponsor.dashboard.label.invoice.deviationInvoicesQuantity"/></th>
		<td><acme:print value="${gbpDeviationInvoicesQuantity}"/></td>
	</tr>
</table>