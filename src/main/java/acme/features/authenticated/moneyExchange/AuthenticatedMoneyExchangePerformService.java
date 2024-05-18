
package acme.features.authenticated.moneyExchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.services.AbstractService;
import acme.forms.MoneyExchange;

@Service
public class AuthenticatedMoneyExchangePerformService extends AbstractService<Authenticated, MoneyExchange> {

	@Autowired
	private AuthenticatedMoneyExchangeRepository repository;

}
