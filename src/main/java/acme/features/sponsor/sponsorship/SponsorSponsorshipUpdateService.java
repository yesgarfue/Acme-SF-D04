
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsorship.Sponsorship;
import acme.enumerate.SponsorshipType;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class SponsorSponsorshipUpdateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int shipId;
		Sponsor sponsor;
		Sponsorship sp;

		shipId = super.getRequest().getData("id", int.class);
		sp = this.repository.findSponsorshipById(shipId);
		sponsor = sp == null ? null : sp.getSponsor();
		status = sponsor != null && !sp.isPublished() && super.getRequest().getPrincipal().hasRole(Sponsor.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;

		int id = super.getRequest().getData("id", int.class);
		object = this.repository.findSponsorshipById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsorship object) {
		assert object != null;

		int projectId;
		Project project;

		projectId = super.getRequest().getData("project", int.class);
		project = this.repository.findOneProjectById(projectId);

		super.bind(object, "code", "moment", "startDate", "finishDate", "amount", "sponsorshipType", "email", "link");
		object.setProject(project);
	}

	@Override
	public void validate(final Sponsorship object) {
		assert object != null;
		String dateString = "2200/12/31 23:59";
		Date limitDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null || existing.equals(object), "code", "Code-duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("moment"))
			super.state(object.getMoment() != null, "moment", "Moment-cannot-be-empty");

		if (!super.getBuffer().getErrors().hasErrors("startDate")) {
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "must-be-date-after-moment");
			super.state(MomentHelper.isBefore(object.getStartDate(), limitDate), "startDate", "startDate-error-date-out-of-bounds");
		}

		if (object.getFinishDate() != null)
			if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
				super.state(MomentHelper.isAfter(object.getFinishDate(), object.getStartDate()), "finishDate", "must-be-date-after-startDate ");
				super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), 1, ChronoUnit.MONTHS), "finishDate", "must-be-at-least-one-month-away");
				super.state(MomentHelper.isBefore(object.getFinishDate(), limitDate), "finishDate", "finishDate-error-date-out-of-bounds");
			}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Double isAmount = object.getAmount().getAmount();
			super.state(isAmount >= 0, "amount", "Amount-must-be-positive-or-zero ");
			super.state(isAmount <= 1_000_000.00, "amount", "Amount-out-of-bounds ");

			String isCurrency = object.getAmount().getCurrency();
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
			super.state(currencyOk, "amount", "Currency-not-supported ");
		}

		if (!super.getBuffer().getErrors().hasErrors("sponsorshipType")) {
			SponsorshipType isType = object.getSponsorshipType();
			super.state(isType.equals(SponsorshipType.FINANCIAL) || isType.equals(SponsorshipType.INKIND), "sponsorshipType", "Invalid-sponsorshipType");
		}

		if (!super.getBuffer().getErrors().hasErrors("project")) {
			Project existing;

			existing = this.repository.findOneProjectByCode(object.getProject().getCode());
			super.state(existing != null, "project", "Invalid-project-code");
		}
	}

	@Override
	public void perform(final Sponsorship object) {
		assert object != null;

		this.repository.save(object);
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

		dataset = super.unbind(object, "code", "moment", "startDate", "finishDate", "amount", "email", "link");
		dataset.put("project", choices.getSelected().getKey());
		dataset.put("sponsorshipType", choicesEnum);
		dataset.put("projects", choices);

		super.getResponse().addData(dataset);
	}
}
