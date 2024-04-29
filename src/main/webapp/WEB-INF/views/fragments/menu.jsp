<%--
- menu.jsp
-
- Copyright (C) 2012-2024 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-yesica" action="https://archive.org/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-oscar" action="https://www.marca.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-lidia" action="https://i.pinimg.com/originals/da/18/38/da18386cc9b32a3f09350f378a04e793.gif"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-alvaro" action="https://media0.giphy.com/media/v1.Y2lkPTc5MGI3NjExMnY5OWtvZmRkbnNlYmdtcXlleWFzbGh3cW1oOXE3YmVhZnRnMnpmZCZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9Zw/6W58C44RnFnEap4qK1/giphy.gif"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link-carlos" action="https://www.realmadrid.com/es-ES"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.config" action="/administrator/system-configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/administrator-dashboard/show"/>
			<acme:menu-suboption code="master.menu.administrator.banner" action="/administrator/banner/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.manager" access="hasRole('Manager')">
			<acme:menu-suboption code="master.menu.manager.projects" action="/manager/project/list"/>
			<acme:menu-suboption code="master.menu.manager.user-stories" action="/manager/user-story/list"/>
			<acme:menu-suboption code="master.menu.manager.dashboard" action="/manager/manager-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.auditor" access="hasRole('Auditor')">
			<acme:menu-suboption code="master.menu.auditor.code-audit" action="/auditor/code-audit/list"/>
			<acme:menu-suboption code="master.menu.auditor.audit-records" action="/auditor/audit-records/list"/>
			<acme:menu-suboption code="master.menu.auditor.dashboard" action="/auditor/auditor-dashboard/show"/>			
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.any">
			<acme:menu-suboption code="master.menu.any.projects" action="/any/project/list"/>
			<acme:menu-suboption code="master.menu.any.claims" action="/any/claim/list"/>
			<acme:menu-suboption code="master.menu.any.trainingModules" action="/any/training-module/list"/>
			<acme:menu-suboption code="master.menu.any.sponsorships" action="/any/sponsorship/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.developer" access="hasRole('Developer')">
			<acme:menu-suboption code="master.menu.developer.trainingModules" action="/developer/training-module/list"/>
			<acme:menu-suboption code="master.menu.developer.trainingSession" action="/developer/training-session/list"/>
			<acme:menu-suboption code="master.menu.developer.dashboard" action="/developer/developer-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sponsor" access="hasRole('Sponsor')">
			<acme:menu-suboption code="master.menu.sponsor.allSponsorship" action="/sponsor/sponsorship/list"/>
			<acme:menu-suboption code="master.menu.sponsor.mySponsorship" action="/sponsor/sponsorship/my-list"/>
			<acme:menu-suboption code="master.menu.sponsor.dashboard" action="/sponsor/sponsor-dashboard/show"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.client" access="hasRole('Client')">
			<acme:menu-suboption code="master.menu.client.contracts" action="/client/contract/list"/>
			<acme:menu-suboption code="master.menu.client.progressLogs" action="/client/progress-log/list"/>
			<acme:menu-suboption code="master.menu.client.dashboard" action="/client/client-dashboard/show"/>
		</acme:menu-option>
	</acme:menu-left>
	
	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/anonymous/system/sign-in" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-manager" action="/authenticated/manager/create" access="!hasRole('Manager')"/>
			<acme:menu-suboption code="master.menu.user-account.manager" action="/authenticated/manager/update" access="hasRole('Manager')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-developer" action="/authenticated/developer/create" access="!hasRole('Developer')"/>
			<acme:menu-suboption code="master.menu.user-account.developer" action="/authenticated/developer/update" access="hasRole('Developer')"/>
			
			<acme:menu-suboption code="master.menu.user-account.become-sponsor" action="/authenticated/sponsor/create" access="!hasRole('Sponsor')"/>
			<acme:menu-suboption code="master.menu.user-account.sponsor" action="/authenticated/sponsor/update" access="hasRole('Sponsor')"/>
		</acme:menu-option>
		<acme:menu-option code="master.menu.sign-out" action="/authenticated/system/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

