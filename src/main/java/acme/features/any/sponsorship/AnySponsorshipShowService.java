
package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsorship.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	//Internal state ---------------------------------------
	@Autowired
	private AnySponsorshipRepository repository;


	//Abstract interface -----------------------------------
	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Sponsorship object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;

		Dataset dataset;
		dataset = super.unbind(object, "code", "moment", "startDate", "finishDate", "amount", "email", "link", "sponsorshipType", "project");
		dataset.put("project", object.getProject().getCode());
		super.getResponse().addData(dataset);
	}
}
