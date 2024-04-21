
package acme.features.auditor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	}

}
