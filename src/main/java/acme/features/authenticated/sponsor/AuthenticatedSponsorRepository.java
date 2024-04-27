
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("SELECT ua FROM UserAccount ua WHERE ua.id= :userAccountId")
	UserAccount findOneUserAccountById(int userAccountId);
}
