
package acme.testing.training.trainingSession;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class DeveloperTrainingSessionShowTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/developer/trainingSession/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test001Positive(final int index, final String code, final String startDate, final String endDate, final String location, final String instructor) {
		super.signIn("developer1", "developer1");

		super.clickOnMenu("Developer", "Training sessions");
		super.checkListingExists();
		super.clickOnListingRecord(index);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("location", location);
		super.checkInputBoxHasValue("instructor", instructor);

		super.signOut();
	}

	@Test
	public void test002Negative() {
	}

	@Test
	public void test300Hacking() {
		super.checkLinkExists("Sign in");
		super.request("/developer/training-session/show");
		super.checkPanicExists();

		super.signIn("administrator", "administrator");
		super.request("/developer/training-session/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("manager1", "manager1");
		super.request("/developer/training-session/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("client1", "client1");
		super.request("/developer/training-session/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("sponsor1", "sponsor1");
		super.request("/developer/training-session/show");
		super.checkPanicExists();
		super.signOut();

		super.signIn("auditor1", "auditor1");
		super.request("/developer/training-session/show");
		super.checkPanicExists();
		super.signOut();
	}

}
