
package acme.features.authenticated.moneyExchange;

import org.springframework.stereotype.Service;

import acme.client.data.accounts.Authenticated;
import acme.client.services.AbstractService;
import acme.forms.MoneyExchange;

@Service
public class AuthenticatedMoneyExchangePerformService extends AbstractService<Authenticated, MoneyExchange> {

	/*
	 * @Autowired
	 * private AuthenticatedMoneyExchangeRepository repository;
	 * 
	 * 
	 * @Override
	 * public void authorise() {
	 * super.getResponse().setAuthorised(true);
	 * }
	 * 
	 * @Override
	 * public void load() {
	 * MoneyExchange object;
	 * object = new MoneyExchange();
	 * 
	 * super.getBuffer().addData(object);
	 * }
	 * 
	 * @Override
	 * public void bind(final MoneyExchange object) {
	 * assert object != null;
	 * super.bind(object, "source", "targetCurrency", "date", "target");
	 * }
	 * 
	 * @Override
	 * public void validate(final MoneyExchange object) {
	 * assert object != null;
	 * 
	 * if (!super.getBuffer().getErrors().hasErrors("source")) {
	 * String isCurrency = object.getSource().getCurrency();
	 * List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
	 * final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
	 * super.state(currencyOk, "source", "Currency-not-supported ");
	 * }
	 * }
	 * 
	 * @Override
	 * public void perform(final MoneyExchange object) {
	 * assert object != null;
	 * 
	 * }
	 * 
	 * @Override
	 * public void unbind() {
	 * }
	 */

}
