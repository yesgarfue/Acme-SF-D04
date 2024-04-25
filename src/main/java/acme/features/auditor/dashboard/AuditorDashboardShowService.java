
package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {

		final boolean status = super.getRequest().getPrincipal().hasRole(Auditor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		AuditorDashboard dashBoard;
		int id = super.getRequest().getPrincipal().getActiveRoleId();
		Integer totalNumberOfStaticCodeAudits = this.repository.totalNumberOfStaticCodeAudits(id);
		Integer totalNumberOfDynamicCodeAudits = this.repository.totalNumberOfDynamicCodeAudits(id);

		Double averageNumberOfAuditRecords = null;
		Double deviationOfAuditRecords = null;
		Double minimumNumberOfAuditRecords = null;
		Double maximumNumberOfAuditRecords = null;
		Double averageTimeOfAuditRecords = null;
		Double timeDeviationOfAuditRecords = null;
		Double minimumTimeOfAuditRecords = null;
		Double maximumTimeOfAuditRecords = null;

		averageNumberOfAuditRecords = this.repository.averageNumberOfAuditRecords(id);
		deviationOfAuditRecords = this.repository.deviationOfAuditRecords(id);
		minimumNumberOfAuditRecords = this.repository.minimumNumberOfAuditRecords(id);
		maximumNumberOfAuditRecords = this.repository.maximumNumberOfAuditRecords(id);
		averageTimeOfAuditRecords = this.repository.averageTimeOfAuditRecords(id);
		timeDeviationOfAuditRecords = this.repository.timeDeviationOfAuditRecords(id);
		minimumTimeOfAuditRecords = this.repository.minimumTimeOfAuditRecords(id);
		maximumTimeOfAuditRecords = this.repository.maximumTimeOfAuditRecords(id);

		dashBoard = new AuditorDashboard();
		dashBoard.setTotalNumberOfStaticCodeAudits(totalNumberOfStaticCodeAudits);
		dashBoard.setTotalNumberOfDynamicCodeAudits(totalNumberOfDynamicCodeAudits);
		dashBoard.setAverageNumberOfAuditingRecords(averageNumberOfAuditRecords);
		dashBoard.setDeviationOfAuditingRecords(deviationOfAuditRecords);
		dashBoard.setMinimumNumberOfAuditingRecords(minimumNumberOfAuditRecords);
		dashBoard.setMaximumNumberOfAuditingRecords(maximumNumberOfAuditRecords);
		dashBoard.setAverageTimeOfAuditingRecords(averageTimeOfAuditRecords);
		dashBoard.setTimeDeviationOfAuditingRecords(timeDeviationOfAuditRecords);
		dashBoard.setMinimumTimeOfAuditingRecords(minimumTimeOfAuditRecords);
		dashBoard.setMaximumTimeOfAuditingRecords(maximumTimeOfAuditRecords);

		super.getBuffer().addData(dashBoard);
	}

	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "totalNumberOfStaticCodeAudits", "totalNumberOfDynamicCodeAudits", "averageNumberOfAuditingRecords", "deviationOfAuditingRecords", "minimumNumberOfAuditingRecords", "maximumNumberOfAuditingRecords",
			"averageTimeOfAuditingRecords", "timeDeviationOfAuditingRecords", "minimumTimeOfAuditingRecords", "maximumTimeOfAuditingRecords");
		super.getResponse().addData(dataset);
	}

}
