
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoice.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListAllService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int id;
		Sponsor sponsor;

		id = super.getRequest().getPrincipal().getActiveRoleId();
		sponsor = this.repository.findOneSponsorbyId(id);
		status = sponsor != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		Principal principal;

		principal = super.getRequest().getPrincipal();
		objects = this.repository.findMyInvoicesBySponsorId(principal.getActiveRoleId());

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;

		Dataset dataset;
		double totalAmount;
		String state = "Yeah";

		totalAmount = object.totalAmount();
		Boolean temp = object.isPublished();

		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "sponsorship.code", "isPublished");
		dataset.put("totalAmount", totalAmount);

		if (temp.equals(true))
			dataset.put("state", state);
		else {
			state = "No";
			dataset.put("state", state);
		}

		super.getResponse().addData(dataset);
	}
}
