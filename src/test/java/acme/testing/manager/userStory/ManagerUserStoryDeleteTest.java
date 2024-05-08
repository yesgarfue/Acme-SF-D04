
package acme.testing.manager.userStory;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.projects.UserStory;
import acme.testing.TestHarness;

public class ManagerUserStoryDeleteTest extends TestHarness {

	// Internal data ----------------------------------------------------------

	@Autowired
	private ManagerUserStoryTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/manager/userStory/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String oldTitle, final String newTitle) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "User stories");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, oldTitle);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");

		super.clickOnMenu("Manager", "User stories");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, newTitle);

		super.signOut();

	}

	@Test
	public void test200Negative() {

	}

	@Test
	public void test300Hacking() {

		final Collection<UserStory> userStories = this.repository.findUserStoriesByManager("manager1");

		super.checkLinkExists("Sign in");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager2", "manager2");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

	@Test
	public void test301Hacking() {

		final Collection<UserStory> userStories = this.repository.findUserStoriesNotInDraftModeByManager("manager1");
		super.checkLinkExists("Sign in");
		super.signIn("manager1", "manager1");
		for (final UserStory userStory : userStories) {
			super.request("/manager/user-story/delete", "id=" + userStory.getId());
			super.checkPanicExists();
		}
		super.signOut();
	}
}
