
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int shipId;
		Sponsorship sponsorship;

		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(shipId);
		status = sponsorship != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int shipId;

		shipId = super.getRequest().getData("shipId", int.class);
		objects = this.repository.findManyInvoicesByShipId(shipId);

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");

		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> objects) {
		assert objects != null;

		int shipId;
		Sponsorship sponsorship;
		final boolean showCreate;

		shipId = super.getRequest().getData("shipId", int.class);
		sponsorship = this.repository.findOneSponsorshipById(shipId);
		showCreate = super.getRequest().getPrincipal().hasRole(sponsorship.getSponsor());

		super.getResponse().addGlobal("shipId", shipId);
		super.getResponse().addGlobal("showCreate", showCreate);
	}
}