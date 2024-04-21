
package acme.features.sponsor.invoices;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsor.Invoice;
import acme.roles.Sponsor;

@Controller
public class SponsorInvoiceController extends AbstractController<Sponsor, Invoice> {

	@Autowired
	private SponsorInvoiceListService		listService;

	@Autowired
	private SponsorInvoiceShowService		showtService;

	@Autowired
	private SponsorInvoiceUpdateService		updateService;

	@Autowired
	private SponsorInvoiceDeleteService		deleteService;

	@Autowired
	private SponsorInvoicePublishService	publishService;

	@Autowired
	private SponsorInvoiceCreateService		createService;


	@PostConstruct
	private void initilise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showtService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
