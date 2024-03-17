
package acme.features.administrator.systemConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class AdministratorSystemConfigurationAddService extends AbstractService<Administrator, SystemConfiguration> {

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

		final String newCurrency = super.getRequest().getData("newCurrency", String.class);
		if (newCurrency == null || newCurrency.trim().isEmpty())
			super.state(false, "newCurrency", "administrator.config.form.error.blank");
		else {
			if (!newCurrency.matches("^[A-Z]{3}$"))
				super.state(false, "newCurrency", "administrator.config.form.error.pattern");
			if (object.getAceptedCurrencies().contains(newCurrency))
				super.state(false, "newCurrency", "administrator.config.form.error.duplicated");
		}
	}

	@Override
	public void perform(final SystemConfiguration object) {
		assert object != null;

		object.setAceptedCurrencies(object.getAceptedCurrencies() + "," + super.getRequest().getData("newCurrency", String.class));

		this.repository.save(object);
	}

	@Override
	public void unbind(final SystemConfiguration object) {
		assert object != null;

		Dataset dataset;
		final String newCurrency = "";
		dataset = super.unbind(object, "systemCurrency", "aceptedCurrencies");
		dataset.put("newCurrency", newCurrency);
		super.getResponse().addData(dataset);
	}

}
