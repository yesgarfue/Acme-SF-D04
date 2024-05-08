
package acme.testing.manager.userStory;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.projects.UserStory;
import acme.testing.TestHarness;

public class ManagerUserStoryShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerUserStoryTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/manager/userStory/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String description, final String estimatedCost, final String acceptanceCriteria, final String priority, final String link, final String draftMode) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "User stories");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("estimatedCost", estimatedCost);
		super.checkInputBoxHasValue("acceptanceCriteria", acceptanceCriteria);
		super.checkInputBoxHasValue("priority", priority);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draftMode", draftMode);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		final Collection<UserStory> userStories = this.repository.findUserStoriesByManager("manager1");

		super.checkLinkExists("Sign in");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager2", "manager2");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		userStories.forEach(userStory -> {
			super.request("/manager/user-story/show", "id=" + userStory.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

}
