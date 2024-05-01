
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.sponsor.Sponsorship;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	//internal state ------------------------
	@Autowired
	private AnySponsorshipRepository repository;

	//Abstract interface --------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Sponsorship> objects;
		//findSponsorshipPublicados
		objects = this.repository.findAllPublishedSponsorships();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Sponsorship object) {
		assert object != null;
		Dataset dataset;

		dataset = super.unbind(object, "code", "startDate", "sponsorshipType", "link", "project.code");

		super.getResponse().addData(dataset);
	}
}
