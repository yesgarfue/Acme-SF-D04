
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsor.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipMyListService		myListService;

	@Autowired
	private SponsorSponsorshipShowService		showService;

	@Autowired
	private SponsorSponsorshipUpdateService		updateService;

	@Autowired
	private SponsorSponsorshipDeleteService		deleteService;

	@Autowired
	private SponsorSponsorshipCreateService		createService;

	@Autowired
	private SponsorSponsorshipPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("create", this.createService);

		super.addCustomCommand("my-list", "list", this.myListService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
