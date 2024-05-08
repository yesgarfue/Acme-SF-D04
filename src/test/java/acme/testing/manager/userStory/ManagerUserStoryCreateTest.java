
package acme.testing.manager.userStory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ManagerUserStoryCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/userStory/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String title, final String description, final String estimatedCost, final String acceptanceCriteria, final String priority, final String link) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "User stories");
		super.clickOnButton("Create User Story");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("estimatedCost", estimatedCost);
		super.fillInputBoxIn("acceptanceCriteria", acceptanceCriteria);
		super.fillInputBoxIn("priority", priority);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create User Story");

		super.clickOnMenu("Manager", "User stories");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, description);
		super.checkColumnHasValue(recordIndex, 2, "true");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("estimatedCost", estimatedCost);
		super.checkInputBoxHasValue("acceptanceCriteria", acceptanceCriteria);
		super.checkInputBoxHasValue("priority", priority);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draftMode", "true");

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/userStory/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String title, final String description, final String estimatedCost, final String acceptanceCriteria, final String priority, final String link) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("manager", "User stories");
		super.clickOnButton("Create User Story");
		super.checkFormExists();

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("estimatedCost", estimatedCost);
		super.fillInputBoxIn("acceptanceCriteria", acceptanceCriteria);
		super.fillInputBoxIn("priority", priority);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create User Story");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/manager/user-story/create");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/manager/user-story/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		super.request("/manager/user-story/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		super.request("/manager/user-story/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		super.request("/manager/user-story/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		super.request("/manager/user-story/create");
		super.checkPanicExists();
		super.signOut();
	}
}
