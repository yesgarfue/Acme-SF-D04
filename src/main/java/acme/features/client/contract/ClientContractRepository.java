
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;
import acme.entities.projects.Project;
import acme.roles.Client;

public interface ClientContractRepository extends AbstractRepository {

	@Query("SELECT c FROM Client c WHERE c.id = :id")
	Client findClientById(int id);

	@Query("SELECT c FROM Contract c WHERE c.client.id = :clientId")
	Collection<Contract> findManyContractsByClientId(int clientId);

	@Query("SELECT c FROM Contract c WHERE c.id = :id")
	Contract findContractById(int id);

	@Query("SELECT c FROM Contract c WHERE c.code = :code")
	Contract findContractByCode(String code);

	@Query("SELECT c FROM Contract c")
	Collection<Contract> findAllContracts();

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

}
