
package acme.testing.manager.project;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.projects.Project;
import acme.testing.TestHarness;

public class ManagerProjectUpdateTest extends TestHarness {
	// Internal data ----------------------------------------------------------

	@Autowired
	private ManagerProjectTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int oldRecordIndex, final int newRecordIndex, final String oldCode, final String newCode, final String title, final String abstracts, final String indication, final String cost, final String link,
		final String draftMode) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(oldRecordIndex, 0, oldCode);
		super.clickOnListingRecord(oldRecordIndex);
		super.checkFormExists();

		super.fillInputBoxIn("code", newCode);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("draftMode", draftMode);
		super.clickOnSubmit("Update");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(newRecordIndex, 0, newCode);
		super.checkColumnHasValue(newRecordIndex, 1, title);
		super.checkColumnHasValue(newRecordIndex, 2, draftMode);

		super.clickOnListingRecord(newRecordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", newCode);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstracts", abstracts);
		super.checkInputBoxHasValue("indication", indication);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draftMode", draftMode);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String oldCode, final String newCode, final String title, final String abstracts, final String indication, final String cost, final String link, final String draftMode) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, oldCode);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.fillInputBoxIn("code", newCode);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("abstracts", abstracts);
		super.fillInputBoxIn("indication", indication);
		super.fillInputBoxIn("cost", cost);
		super.fillInputBoxIn("link", link);
		super.fillInputBoxIn("draftMode", draftMode);
		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();

	}

	@ParameterizedTest //Mismo csv que el 200Negative delete. Proyecto en modo draft-mode false
	@CsvFileSource(resources = "/manager/project/update-negative2.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String code) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Update");

		super.signOut();

	}
	@Test
	public void test300Hacking() {

		final Collection<Project> projects = this.repository.findProjectsByManager("manager1");

		super.checkLinkExists("Sign in");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager2", "manager2");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		projects.forEach(project -> {
			super.request("/manager/project/update", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
