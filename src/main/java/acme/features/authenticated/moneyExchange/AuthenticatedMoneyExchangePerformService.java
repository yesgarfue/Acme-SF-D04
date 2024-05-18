
package acme.features.authenticated.moneyExchange;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import acme.client.data.accounts.Authenticated;
import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.ExchangeRate;
import acme.forms.MoneyExchange;
import acme.systemConfiguration.SystemConfiguration;

@Service
public class AuthenticatedMoneyExchangePerformService extends AbstractService<Authenticated, MoneyExchange> {

	@Autowired
	private AuthenticatedMoneyExchangeRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		MoneyExchange object;

		object = new MoneyExchange();

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final MoneyExchange object) {
		assert object != null;

		super.bind(object, "source", "targetCurrency", "date", "target");
	}

	@Override
	public void validate(final MoneyExchange object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("source")) {
			String isCurrency = object.getSource().getCurrency();
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals(isCurrency));
			super.state(currencyOk, "source", "Currencies-not-accepted");
		}

		if (!super.getBuffer().getErrors().hasErrors("targetCurrency")) {
			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean currencyOk = Stream.of(sc.get(0).aceptedCurrencies.split(",")).anyMatch(c -> c.equals("targetCurrency"));
			super.state(currencyOk, "targetCurrency", "Currencies-not-accepted");
		}
	}

	@Override
	public void perform(final MoneyExchange object) {
		assert object != null;

		Money source, target;
		String targetCurrency;
		Date date;
		MoneyExchange exchange;

		source = object.getSource();
		targetCurrency = object.getTargetCurrency();
		exchange = this.computeMoneyExchange(source, targetCurrency);

		target = exchange.getTarget();
		object.setTarget(target);
		date = exchange.getDate();
		object.setDate(date);
	}

	@Override
	public void unbind(final MoneyExchange object) {
		assert object != null;

		Dataset dataset;

		dataset = super.unbind(object, "source", "targetCurrency", "date", "target");

		super.getResponse().addData(dataset);
	}

	// Ancillary methods ------------------------------------------------------

	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		//assert !StringHelper.isBlank(targetCurrency);

		MoneyExchange response;
		RestTemplate api;
		HttpHeaders headers;
		HttpEntity<String> parameters;
		ResponseEntity<ExchangeRate> objectResponse;
		ExchangeRate record;
		String sourceCurrency;
		Double sourceAmount, targetAmount, rate;
		Money target;
		Date moment;

		try {
			api = new RestTemplate();

			sourceAmount = source.getAmount();
			sourceCurrency = source.getCurrency();

			headers = new HttpHeaders();
			headers.add("apikey", "wLIxo1nnIv5zaTqUNHMHDWXJDpaOhls8");

			parameters = new HttpEntity<String>("parameters", headers);

			objectResponse = api.exchange("https://api.apilayer.com/exchangerates_data/latest?base={0}&symbols={1}", HttpMethod.GET, parameters, ExchangeRate.class, //
				"cur_live_9CS0QA54yYzg4W3iJ1QQMMktAfPY2DpLVpUjpPKP", sourceCurrency, targetCurrency);
			/*
			 * if (objectResponse != null && objectResponse.getRates() != null) {
			 * Double rate = objectResponse.getRates().get("EUR");
			 * if (rate != null) {
			 * // Usa la tasa de cambio `rate`
			 * } else {
			 * // Maneja el caso donde la clave "EUR" no está presente en el mapa
			 * }
			 * } else {
			 * // Maneja el caso donde `objectResponse` o `rates` son `null`
			 * }
			 */

			assert objectResponse != null && objectResponse.getBody() != null;
			record = objectResponse.getBody();

			assert record != null && record.getRates().containsKey(targetCurrency);
			rate = record.getRates().get(targetCurrency);

			targetAmount = rate * sourceAmount;

			target = new Money();
			target.setAmount(targetAmount);
			target.setCurrency(targetCurrency);

			moment = record.getDate();

			response = new MoneyExchange();
			response.setSource(source);
			response.setTargetCurrency(targetCurrency);
			response.setDate(moment);
			response.setTarget(target);

			MomentHelper.sleep(1000); // HINT: need to pause the requests to the API a bit down to prevent DOS attacks
		} catch (final Throwable oops) {
			response = null;
		}

		return response;
	}
}
