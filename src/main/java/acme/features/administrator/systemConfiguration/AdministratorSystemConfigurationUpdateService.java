
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationUpdateService extends AbstractService<Administrator, SystemConfiguration> {

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
	public void bind(final SystemConfiguration object) {
		assert object != null;

		super.bind(object, "systemCurrency", "aceptedCurrencies");

	}

	@Override
	public void validate(final SystemConfiguration object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("systemCurrency")) {
			final String[] currencies = object.getAceptedCurrencies().split(",");
			boolean currencyExists = false;
			for (final String currency : currencies)
				if (object.getSystemCurrency().equals(currency)) {
					currencyExists = true;
					break;
				}
			super.state(currencyExists, "systemCurrency", "administrator.config.form.error.unavailable");
		}
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "systemCurrency", "aceptedCurrencies");
		super.getResponse().addData(dataset);
	}

}
