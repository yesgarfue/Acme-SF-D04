
package acme.features.authenticated.moneyExchange;

import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.services.AbstractService;
import acme.forms.MoneyExchange;

@Service
public class AuthenticatedMoneyExchangePerformService extends AbstractService<Authenticated, MoneyExchange> {

}
