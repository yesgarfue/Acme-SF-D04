
package acme.features.any.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.sponsor.Sponsorship;

@Controller
public class AnySponsorshipController extends AbstractController<Any, Sponsorship> {

	//Internal state -------------------------

	@Autowired
	private AnySponsorshipListService	listService;

	@Autowired
	private AnySponsorshipShowService	showService;


	//Post-Constructor --------------------------------
	@PostConstruct
	private void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}
}
