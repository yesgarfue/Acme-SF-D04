
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

}
