
package acme.testing.training.trainingModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingModulePublishTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test001Positive(final int index, final String code) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(index, 0, code);

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test002Negative(final int index, final String code) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-module/publish");
		super.checkPanicExists();
		super.signOut();
	}
}
