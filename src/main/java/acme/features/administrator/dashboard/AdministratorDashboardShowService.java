
package acme.features.administrator.dashboard;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AdministratorDashboard;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		final boolean status = super.getRequest().getPrincipal().hasRole(Administrator.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AdministratorDashboard dashboard;
		Map<String, Integer> numberOfPrincipalsByRol;

		Integer noticeWithEmailAndLinkRatio;
		Integer criticalObjectiveRatio;
		Integer nonCriticalObjectiveRatio;

		Double averageValueRisk;
		Double minimunValueRisk;
		Double maximunValueRisk;
		Double desviationValueRisk;

		Double averageNumberOfClaimsOverLastTenWeeks;
		Integer minimunNumberOfClaimsOverLastTenWeeks;
		Integer maximunNumberOfClaimsOverLastTenWeeks;
		Double desviationNumberOfClaimsOverLastTenWeeks;

		numberOfPrincipalsByRol = this.repository.numberOfPrincipalsByRol();
		noticeWithEmailAndLinkRatio = this.repository.noticeWithEmailAndLinkRatio();
		criticalObjectiveRatio = this.repository.criticalObjectiveRatio();
		nonCriticalObjectiveRatio = this.repository.nonCriticalObjectiveRatio();

		averageValueRisk = this.repository.averageValueRisk();
		minimunValueRisk = this.repository.minimumValueRisk();
		maximunValueRisk = this.repository.maximumValueRisk();
		desviationValueRisk = this.repository.deviationValueRisk();

		averageNumberOfClaimsOverLastTenWeeks = this.repository.averageNumberOfClaimsOverLastTenWeeks();
		minimunNumberOfClaimsOverLastTenWeeks = this.repository.minimumNumberOfClaimsOverLastTenWeeks();
		maximunNumberOfClaimsOverLastTenWeeks = this.repository.maximumNumberOfClaimsOverLastTenWeeks();
		desviationNumberOfClaimsOverLastTenWeeks = this.repository.deviationNumberOfClaimsOverLastTenWeeks();

		dashboard = new AdministratorDashboard();
		//dashboard.setNumberOfPrincipalsByRol(numberOfPrincipalsByRol);
		dashboard.setNoticeWithEmailAndLinkRatio(noticeWithEmailAndLinkRatio);
		dashboard.setCriticalObjectiveRatio(criticalObjectiveRatio);
		dashboard.setNonCriticalObjectiveRatio(nonCriticalObjectiveRatio);
		dashboard.setAverageValueRisk(averageValueRisk);
		dashboard.setMinimunValueRisk(minimunValueRisk);
		dashboard.setMaximunValueRisk(maximunValueRisk);
		dashboard.setDesviationValueRisk(desviationValueRisk);
		dashboard.setAverageNumberOfClaimsOverLastTenWeeks(averageNumberOfClaimsOverLastTenWeeks);
		dashboard.setMinimunNumberOfClaimsOverLastTenWeeks(minimunNumberOfClaimsOverLastTenWeeks);
		dashboard.setMaximunNumberOfClaimsOverLastTenWeeks(maximunNumberOfClaimsOverLastTenWeeks);
		dashboard.setDesviationNumberOfClaimsOverLastTenWeeks(desviationNumberOfClaimsOverLastTenWeeks);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset;
		Map<String, Integer> numberOfPrincipalsByRol;
		numberOfPrincipalsByRol = this.repository.numberOfPrincipalsByRol();

		dataset = super.unbind(object, "numberOfPrincipalsByRol", "noticeWithEmailAndLinkRatio", "criticalObjectiveRatio", "nonCriticalObjectiveRatio", "averageValueRisk", "minimunValueRisk", "maximunValueRisk", "desviationValueRisk",
			"averageNumberOfClaimsOverLastTenWeeks", "minimunNumberOfClaimsOverLastTenWeeks", "maximunNumberOfClaimsOverLastTenWeeks", "desviationNumberOfClaimsOverLastTenWeeks");

		dataset.put("roles", numberOfPrincipalsByRol.toString().replace("{", "").replace("}", "").replace("=", ": "));
		super.getResponse().addData(dataset);
	}

}
