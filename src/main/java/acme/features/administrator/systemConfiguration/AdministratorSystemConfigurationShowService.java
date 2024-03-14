
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationShowService extends AbstractService<Administrator, SystemConfiguration> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorSystemConfigurationRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		SystemConfiguration object;

		object = this.repository.findSystemConfiguration();
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "systemCurrency", "aceptedCurrencies");
		super.getResponse().addData(dataset);
	}

}
