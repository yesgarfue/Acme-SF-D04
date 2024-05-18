
package acme.features.sponsor.sponsorship;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.projects.Project;
import acme.entities.sponsor.Sponsorship;
import acme.enumerate.SponsorshipType;
import acme.roles.Sponsor;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	private SponsorSponsorshipRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsorship object;
		Sponsor sponsor;

		sponsor = this.repository.findOneSponsorbyId(super.getRequest().getPrincipal().getActiveRoleId());
		object = new Sponsorship();
		object.setSponsor(sponsor);
		object.setPublished(false);
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

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Sponsorship existing;

			existing = this.repository.findOneSponsorshipByCode(object.getCode());
			super.state(existing == null, "code", "Code-duplicated");
		}

		if (!super.getBuffer().getErrors().hasErrors("moment"))
			super.state(object.getMoment() != null, "moment", "Moment-cannot-be-empty");

		//* LIMITES PERMITIDOS DE FECHAS Y COMPROBAR LAS FECHAS PROBLEMATICAS
		if (!super.getBuffer().getErrors().hasErrors("startDate"))
			super.state(MomentHelper.isAfter(object.getStartDate(), object.getMoment()), "startDate", "must-be-date-after-moment");

		//* LIMITES PERMITIDOS DE FECHAS Y COMPROBAR LAS FECHAS PROBLEMATICAS: AÑO BISIESTO NO RECIBE 
		if (object.getFinishDate() != null)
			if (!super.getBuffer().getErrors().hasErrors("finishDate")) {
				super.state(MomentHelper.isAfter(object.getFinishDate(), object.getStartDate()), "finishDate", "must-be-date-after-startDate ");
				super.state(MomentHelper.isLongEnough(object.getStartDate(), object.getFinishDate(), 1, ChronoUnit.MONTHS), "finishDate", "must-be-at-least-one-month-away");
			}

		if (!super.getBuffer().getErrors().hasErrors("amount")) {
			Double isAmount = object.getAmount().getAmount();
			super.state(isAmount != null && isAmount > 0, "amount", "Amount-cannot-be-negative-or-zero ");

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
			super.state(existing != null && !existing.isDraftMode(), "project", "Invalid-project-code");
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
