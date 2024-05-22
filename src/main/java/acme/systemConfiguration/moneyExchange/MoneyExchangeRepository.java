
package acme.systemConfiguration.moneyExchange;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface MoneyExchangeRepository extends AbstractRepository {

	@Query("SELECT sc FROM SystemConfiguration sc")
	List<SystemConfiguration> findSystemConfiguration();

	@Query("SELECT me FROM MoneyExchange me WHERE me.currencyOriginal = :currencyOriginal")
	MoneyExchange findLastMoneyExchange(String currencyOriginal);

	@Query("SELECT me FROM MoneyExchange me WHERE me.currencyOriginal = :currencyOriginal AND me.currencyTarget = :currencyTarget")
	MoneyExchange findLastMoneyExchangeForCurrency(String currencyOriginal, String currencyTarget);
}
