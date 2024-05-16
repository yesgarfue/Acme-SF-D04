
package acme.testing.training.trainingModule;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingModuleListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingModule/list-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int index, final String code, final String creationMoment, final String difficultyLevel) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training modules");
		//super.checkNotListingExists();

		super.checkColumnHasValue(index, 0, code);
		super.checkColumnHasValue(index, 1, creationMoment);
		super.checkColumnHasValue(index, 2, difficultyLevel);

		super.signOut();
	}

	@Test
	public void test200Negative() {
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-module/list");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-module/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-module/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-module/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-module/list");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-module/list");
		super.checkPanicExists();
		super.signOut();
	}

}
