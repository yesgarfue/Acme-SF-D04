
package acme.testing.training.trainingSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingSessionUpdateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String code, final String startDate, final String endDate, final String location, final String instructor, final String email, final String trainingModule) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("location", location);
		super.fillInputBoxIn("instructor", instructor);
		super.fillInputBoxIn("contactEmail", email);
		super.fillInputBoxIn("trainingModule", trainingModule);
		super.clickOnSubmit("Update");

		super.checkListingExists();

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("location", location);
		super.checkInputBoxHasValue("instructor", instructor);
		super.checkInputBoxHasValue("contactEmail", email);
		super.checkInputBoxHasValue("trainingModule", trainingModule);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/update-negative1.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int index, final String code, final String startDate, final String endDate, final String location, final String instructor, final String email, final String trainingModule) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("location", location);
		super.fillInputBoxIn("instructor", instructor);
		super.fillInputBoxIn("contactEmail", email);
		super.fillInputBoxIn("trainingModule", trainingModule);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/update-negative2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int index, final String code) {

		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(index, 0, code);
		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkNotButtonExists("Update");

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-session/update");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-session/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-session/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-session/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-session/update");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-session/update");
		super.checkPanicExists();
		super.signOut();
	}
}
