
package acme.testing.training.trainingModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingModuleShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test001Positive(final int index, final String code, final String creationMoment, final String details, final String difficultyLevel, final String updatedMoment, final String totalTime, final String project) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		super.checkListingExists();
		super.clickOnListingRecord(index);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("details", details);
		super.checkInputBoxHasValue("difficultyLevel", difficultyLevel);
		super.checkInputBoxHasValue("updatedMoment", updatedMoment);
		super.checkInputBoxHasValue("totalTime", totalTime);
		super.checkInputBoxHasValue("project", project);

		super.signOut();
	}

	@Test
	public void test002Negative() {
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-module/show");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-module/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-module/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-module/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-module/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-module/show");
		super.checkPanicExists();
		super.signOut();
	}
}
