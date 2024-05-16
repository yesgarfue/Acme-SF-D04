
package acme.testing.training.trainingModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingModuleCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String code, final String creationMoment, final String difficultyLevel, final String details, final String updatedMoment, final String totalTime, final String project) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		super.checkListingExists();

		super.clickOnButton("Create Training Module");
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("updatedMoment", updatedMoment);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("project", project);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Developer", "Training modules");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(index);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("difficultyLevel", difficultyLevel);
		super.checkInputBoxHasValue("details", details);
		super.checkInputBoxHasValue("updatedMoment", updatedMoment);
		super.checkInputBoxHasValue("totalTime", totalTime);
		super.checkInputBoxHasValue("project", project);

		super.signOut();
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String index, final String code, final String creationMoment, final String difficultyLevel, final String details, final String updatedMoment, final String totalTime, final String project) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		super.clickOnButton("Create Training Module");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("creationMoment", creationMoment);
		super.fillInputBoxIn("difficultyLevel", difficultyLevel);
		super.fillInputBoxIn("details", details);
		super.fillInputBoxIn("updatedMoment", updatedMoment);
		super.fillInputBoxIn("totalTime", totalTime);
		super.fillInputBoxIn("project", project);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-module/create");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-module/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-module/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-module/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-module/create");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-module/create");
		super.checkPanicExists();
		super.signOut();
	}
}
