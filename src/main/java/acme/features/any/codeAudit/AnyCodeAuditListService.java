
package acme.features.any.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.audits.CodeAudit;

@Service
public class AnyCodeAuditListService extends AbstractService<Any, CodeAudit> {

	@Autowired
	private AnyCodeAuditRepository repository;


	@Override
	public void authorise() {
		final boolean status = true;
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<CodeAudit> objects;

		objects = this.repository.findAllPublishedCodeAudit();
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "code", "type", "correctiveActions");

		super.getResponse().addData(dataset);
	}

}
