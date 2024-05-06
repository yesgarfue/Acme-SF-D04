
package acme.testing.manager.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class ManagerProjectCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstracts, final String indication, final String cost, final String link) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.clickOnButton("Create project");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create project");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, title);
		super.checkColumnHasValue(recordIndex, 2, "true");

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstracts", abstracts);
		super.checkInputBoxHasValue("indication", indication);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draftMode", "true");

		super.clickOnButton("User Stories");
		super.checkListingExists();
		super.checkListingEmpty();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String code, final String title, final String abstracts, final String indication, final String cost, final String link) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();

		super.clickOnButton("Create project");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("link", link);
		super.clickOnSubmit("Create project");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/manager/project/create");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/manager/project/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		super.request("/manager/project/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		super.request("/manager/project/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		super.request("/manager/project/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		super.request("/manager/project/create");
		super.checkPanicExists();
		super.signOut();
	}
}
