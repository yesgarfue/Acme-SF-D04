
package acme.features.authenticated.moneyExchange;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Authenticated;
import acme.forms.MoneyExchange;

public class AuthenticatedMoneyExchangeController extends AbstractController<Authenticated, MoneyExchange> {

	@Autowired
	private AuthenticatedMoneyExchangePerformService performService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("perform", this.performService);
	}
}
