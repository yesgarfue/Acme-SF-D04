
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.services.AbstractService;
import acme.roles.Manager;

@Service
public class AuthenticatedManagerCreateService extends AbstractService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedManagerRepository repository;


	@Override
	public void authorise() {
		// TODO Auto-generated method stub
		super.authorise();
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		super.load();
	}

	@Override
	public void unbind(final Manager object) {
		// TODO Auto-generated method stub
		super.unbind(object);
	}

	// AbstractService<Authenticated, Manager> ---------------------------

}
