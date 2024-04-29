
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.data.accounts.UserAccount;
import acme.client.data.models.Dataset;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.roles.Sponsor;

@Service
public class AuthenticatedSponsorUpdateService extends AbstractService<Authenticated, Sponsor> {

	@Autowired
	private AuthenticatedSponsorRepository repository;


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Sponsor.class);
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Sponsor object;
		int sponsorId;
		UserAccount userAccount;

		sponsorId = super.getRequest().getPrincipal().getAccountId();
		object = this.repository.findOneSponsorByAccountId(sponsorId);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Sponsor object) {
		assert object != null;

		super.bind(object, "name", "expectedBenefits", "webPage", "emailContact");
	}

	@Override
	public void validate(final Sponsor object) {
		assert object != null;

		super.state(object.getName() != null && object.getName().length() <= 75, "name", "sponsor.error.name.invalidLength");
		super.state(object.getExpectedBenefits() != null && object.getExpectedBenefits().length() <= 100, "expectedBenefits", "sponsor.error.expectedBenefits.invalidLength");
	}

	@Override
	public void perform(final Sponsor object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Sponsor object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "name", "expectedBenefits", "webPage", "emailContact");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
