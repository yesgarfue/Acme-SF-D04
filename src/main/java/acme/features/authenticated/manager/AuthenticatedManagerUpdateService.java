
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.data.accounts.Authenticated;
import acme.client.services.AbstractService;
import acme.features.authenticated.consumer.AuthenticatedConsumerRepository;
import acme.roles.Manager;

public class AuthenticatedManagerUpdateService extends AbstractService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedConsumerRepository repository;

	// AbstractService interface ----------------------------------------------ç

}
