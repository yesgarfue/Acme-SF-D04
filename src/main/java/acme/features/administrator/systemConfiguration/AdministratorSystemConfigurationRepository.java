
package acme.features.administrator.systemConfiguration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;
import acme.systemConfiguration.SystemConfiguration;

@Repository
public interface AdministratorSystemConfigurationRepository extends AbstractRepository {

	@Query("SELECT a FROM Administrator a WHERE a.id = :id")
	Administrator findOneAdministratorById(int id);

	@Query("SELECT s FROM SystemConfiguration s")
	SystemConfiguration findSystemConfiguration();

}
