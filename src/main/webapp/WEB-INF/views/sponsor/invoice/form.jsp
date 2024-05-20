<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<table class="table table-sm">
	<tr>
		<td><acme:input-money code="sponsor.invoice.label.info.sponsorship.amount" path="amountSponsorship" readonly="true" placeholder="N/A"/></td>
		<td><acme:input-money code="sponsor.invoice.label.info.sponsorship.accumulatedAmountInvoices" path="accumulatedAmountInvoices" readonly="true" placeholder="N/A"/></td>
	</tr>
</table>

<acme:form>
	<acme:input-textbox code="sponsor.invoice.label.code" path="code" placeholder="IN-XXXX-XXXX"/>
	<acme:input-moment code="sponsor.invoice.label.registrationTime" path="registrationTime" readonly="true"/>
	<acme:input-moment code="sponsor.invoice.label.dueDate" path="dueDate" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-money code="sponsor.invoice.label.quantity" path="quantity" placeholder="(EUR/USD/GBP) 00.00"/>
	<acme:input-double code="sponsor.invoice.label.tax" path="tax" placeholder="00.00"/>
	<acme:input-textbox code="sponsor.invoice.label.link" path="link" placeholder="https://www.example.com"/>
	
	<jstl:if test="${_command == 'create'}">
		<acme:input-textbox  code="sponsor.invoice.label.recomendation" path="result" readonly="true"/>
	</jstl:if>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && isPublished == false}">
			<acme:submit code="sponsor.invoice.button.update" action="/sponsor/invoice/update"/>
			<acme:submit code="sponsor.invoice.button.delete" action="/sponsor/invoice/delete"/>
			<acme:submit code="sponsor.invoice.button.publish" action="/sponsor/invoice/publish"/>
		</jstl:when>
		<jstl:when test="${_command =='create'}">
			<acme:submit code="sponsor.invoice.button.create" action="/sponsor/invoice/create?shipId=${shipId}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function() {
			const accumulatedAmountInvoicesField = document.getElementById('amountSponsorship.getAmount().getAmount()');
	    	const amountSponsorshipField = document.getElementById('accumulatedAmountInvoices');
	        const taxField = document.getElementById('tax');
	        const resultField = document.getElementById('result');accumulatedAmountInvoices

	        function updateResult() {
	        	const accumulatedAmountInvoicesValue = parseFloat(accumulatedField.value.replace(/,/g, '')) || 0;
		        const amountSponsorshipValue = parseFloat(fField.value.replace(/,/g, '')) || 0;
	            const taxValue = parseFloat(taxField.value.replace(/,/g, '')) || 0;
	            const result = (amountSponsorshipValue-accumulatedAmountInvoicesValue) / (1 + taxValue / 100);
	            resultField.value = result.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}
	        taxField.addEventListener('input', updateResult);
	});
</script>




