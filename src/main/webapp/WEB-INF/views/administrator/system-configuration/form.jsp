<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-textarea code="administrator.config.form.label.currencies" path="aceptedCurrencies" readonly="true"/>
	<jstl:if test="${_command!='add'}">
		<acme:input-textarea code="administrator.config.form.label.currency" path="systemCurrency"/>
	</jstl:if>
	<jstl:if test="${_command=='add'}">
		<acme:hidden-data path="systemCurrency"/>
		<acme:input-textarea code="administrator.config.form.label.currency.new" path="newCurrency"/>
	</jstl:if>
	
	<acme:submit test="${_command!='add'}" code="administrator.config.form.button.update" action="/administrator/system-configuration/update"/>
	<acme:button test="${_command!='add'}" code="administrator.config.form.button.add" action="/administrator/system-configuration/add"/>
	<acme:submit test="${_command=='add'}" code="administrator.config.form.button.add" action="/administrator/system-configuration/add"/>	
</acme:form>
