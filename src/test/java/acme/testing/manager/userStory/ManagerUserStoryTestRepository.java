
package acme.testing.manager.userStory;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.UserStory;

public interface ManagerUserStoryTestRepository extends AbstractRepository {

	@Query("SELECT l FROM UserStory l WHERE l.manager.userAccount.username = :username")
	Collection<UserStory> findUserStoriesByManager(String username);

	@Query("SELECT l FROM UserStory l WHERE l.manager.userAccount.username = :username AND l.draftMode = false")
	Collection<UserStory> findUserStoriesNotInDraftModeByManager(String username);

}
