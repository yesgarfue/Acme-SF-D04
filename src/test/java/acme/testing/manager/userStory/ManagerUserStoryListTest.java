
package acme.testing.manager.userStory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ManagerUserStoryListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/userStory/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String description, final String draftMode) {

		super.signIn("manager1", "manager1");

		super.clickOnButton("Create User Story");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, description);
		super.checkColumnHasValue(recordIndex, 2, draftMode);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/manager/user-story/list");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/manager/user-story/list");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		super.request("/manager/user-story/list");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		super.request("/manager/user-story/list");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		super.request("/manager/user-story/list");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		super.request("/manager/user-story/list");
		super.checkPanicExists();
		super.signOut();
	}

}
