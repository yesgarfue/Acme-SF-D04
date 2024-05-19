
package acme.systemConfiguration.moneyExchange;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import acme.client.data.datatypes.Money;
import acme.client.helpers.MomentHelper;
import acme.client.helpers.StringHelper;

public class MoneyExchangePerform {

	@Autowired
	private MoneyExchangeRepository repository;


	public MoneyExchange computeMoneyExchange(final Money source, final String targetCurrency) {
		assert source != null;
		assert !StringHelper.isBlank(targetCurrency);

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

			objectResponse = api.exchange("https://api.apilayer.com/exchangerates_data/latest?base={0}&symbols={1}", HttpMethod.GET, parameters, ExchangeRate.class, "cur_live_9CS0QA54yYzg4W3iJ1QQMMktAfPY2DpLVpUjpPKP", sourceCurrency, targetCurrency);
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
