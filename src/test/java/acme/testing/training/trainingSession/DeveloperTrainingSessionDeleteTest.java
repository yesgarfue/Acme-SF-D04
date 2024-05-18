
package acme.testing.training.trainingSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingSessionDeleteTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String oldCode, final String newCode) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(index, 0, oldCode);

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.clickOnSubmit("Delete");
		super.checkNotErrorsExist();
		super.checkColumnHasValue(index, 0, newCode);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int index, final String oldCode, final String newCode) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(index, 0, oldCode);

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkNotButtonExists("Delete");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-session/delete");
		super.checkPanicExists();
		super.signOut();
	}
}
