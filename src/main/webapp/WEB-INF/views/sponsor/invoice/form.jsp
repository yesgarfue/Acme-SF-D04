<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>
	<acme:input-textbox code="sponsor.invoice.label.code" path="code" placeholder="IN-XXXX-XXXX"/>
	<acme:input-moment code="sponsor.invoice.label.registrationTime" path="registrationTime" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-moment code="sponsor.invoice.label.dueDate" path="dueDate" placeholder="YYYY/MM/DD HH:SS"/>
	<acme:input-money code="sponsor.invoice.label.quantity" path="quantity" placeholder="(EUR/USD/GBP) 00.00"/>
	<jstl:if test="${_command == 'create'}">
		<acme:input-double code="sponsor.invoice.form.label.default" path="amountAprox" readonly="true" placeholder="N/A"/>
		<acme:input-textbox code="sponsor.invoice.form.label.default" path="currencyAprox" readonly="true" placeholder="N/A"/>
			<!-- Campo para mostrar el resultado de la multiplicación -->
    <acme:input-textbox  code="sponsor.invoice.label.result" path="result" readonly="true"/>
	</jstl:if>
	<acme:input-double code="sponsor.invoice.label.tax" path="tax" placeholder="00.00"/>
	<acme:input-textbox code="sponsor.invoice.label.link" path="link" placeholder="https://www.example.com"/>
	<jstl:if test="${_command == 'show'}">
		<acme:input-double code="sponsor.invoice.form.label.defgdgfhault" path="amountTotal" readonly="true" placeholder="N/A"/>
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


<!-- Lugar para mostrar el resultado de la multiplicación -->
<p>Multiplicación de f y tax: <span id="multipliedValue">0.00</span></p>

<script type="text/javascript">
	document.addEventListener("DOMContentLoaded", function() {
	    	const fField = document.getElementById('amountAprox');
	        const taxField = document.getElementById('tax');
	        const resultField = document.getElementById('result');

	        function updateResult() {
	        	const fValue = parseFloat(fField.value) || 0;
	            const taxValue = parseFloat(taxField.value) || 0;
	            const result = fValue/(1+taxValue/100);
	            resultField.value = result.toFixed(2);
	        }

	        taxField.addEventListener('input', updateResult);
	    });
	    </script>





