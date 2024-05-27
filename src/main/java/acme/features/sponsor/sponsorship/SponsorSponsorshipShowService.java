
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.enumerate.SponsorshipType;
import acme.roles.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int shipId;
		Sponsor sponsor;
		Sponsorship sponsorship;

		int sponsorId = super.getRequest().getPrincipal().getActiveRoleId();
		shipId = super.getRequest().getData("id", int.class);
		sponsorship = this.repository.findSponsorshipByIdSponsorId(shipId, sponsorId);
		sponsor = sponsorship == null ? null : sponsorship.getSponsor();
		status = sponsor != null && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
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

		Collection<Project> projects;
		SelectChoices choices, choicesEnum;
		Dataset dataset;

		projects = this.repository.findAllProjects();
		choices = SelectChoices.from(projects, "code", object.getProject());
		choicesEnum = SelectChoices.from(SponsorshipType.class, object.getSponsorshipType());

		dataset = super.unbind(object, "code", "moment", "startDate", "finishDate", "amount", "email", "link", "isPublished");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("sponsorshipType", choicesEnum);
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
