<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<jstl:if test="${_command == 'create'}">
	<table class="table table-sm">
		<tr>
			<th scope="row"><acme:message code="sponsor.invoice.label.info.sponsorship"/></th>
			<td><acme:input-double code="sponsor.invoice.label.info.sponsorship.amount" path="sponsorshipAmount" readonly="true" placeholder="N/A"/></td>
			<td><acme:input-textbox code="sponsor.invoice.label.info.sponsorship.currency" path="sponsorshipCurrency" readonly="true" placeholder="N/A"/></td>
		</tr>
		<tr>
			<th scope="row"><acme:message code="sponsor.invoice.label.info.invoice"/></th>
			<td><acme:input-double code="sponsor.invoice.label.info.accumulatedAmountInvoices" path="accumulatedAmountInvoices" readonly="true" placeholder="N/A"/></td>
			<td><acme:input-textbox code="sponsor.invoice.label.recomendation" path="result" readonly="true" placeholder="quantity + (quantity * tax/100)"/></td>
		</tr>
	</table>
</jstl:if>

<acme:form>
	<acme:input-textbox code="sponsor.invoice.label.code" path="code" placeholder="IN-XXXX-XXXX"/>
	<acme:input-moment code="sponsor.invoice.label.registrationTime" path="registrationTime"/>
	<acme:input-moment code="sponsor.invoice.label.dueDate" path="dueDate" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-money code="sponsor.invoice.label.quantity" path="quantity" placeholder="(EUR/USD/GBP) 00.00"/>
	<acme:input-double code="sponsor.invoice.label.tax" path="tax" placeholder="00.00"/>
	<acme:input-textbox code="sponsor.invoice.label.link" path="link" placeholder="https://www.example.com"/>
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-double code="sponsor.invoice.label.totalAmount" path="quantityTax" readonly="true" placeholder="N/A"/>
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
			const totalField = document.getElementById('sponsorshipAmount');
			const accumulatedField = document.getElementById('accumulatedAmountInvoices');
	        const taxField = document.getElementById('tax');
	        const resultField = document.getElementById('result');

	        function updateResult() {
	        	const totalValue = parseFloat(totalField.value.replace(/,/g, '')) || 0;
	        	const accumulatedValue = parseFloat(accumulatedField.value.replace(/,/g, '')) || 0;
	            const taxValue = parseFloat(taxField.value.replace(/,/g, '')) || 0;
	            const result = (totalValue - accumulatedValue) / (1 + taxValue / 100);
	            
	            resultField.value = result.toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
			}
	        taxField.addEventListener('input', updateResult);
	});
</script>




