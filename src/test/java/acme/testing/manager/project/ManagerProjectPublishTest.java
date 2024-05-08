
package acme.testing.manager.project;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.projects.Project;
import acme.testing.TestHarness;

public class ManagerProjectPublishTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("draftMode", "true");
		super.clickOnSubmit("Publish");
		super.checkNotErrorsExist();

		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("draftMode", "false");
		super.checkNotButtonExists("Update");
		super.checkNotButtonExists("publish");
		super.checkNotButtonExists("Publish");
		super.signOut();
	}

	//Draft Mode True y con fatal errors
	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/publish-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);
		super.checkInputBoxHasValue("code", code);

		super.clickOnSubmit("Publish");
		super.checkErrorsExist();

	}

	@ParameterizedTest //Mismo csv que el 200Negative delete. Proyecto en modo draft-mode false
	@CsvFileSource(resources = "/manager/project/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String code) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		final Collection<Project> projects = this.repository.findProjectsByManager("manager1");

		super.checkLinkExists("Sign in");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager2", "manager2");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		projects.forEach(project -> {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

	@Test
	public void test301Hacking() {

		final Collection<Project> projects = this.repository.findProjectsNotInDraftModeByManager("manager2");
		super.checkLinkExists("Sign in");
		super.signIn("manager1", "manager1");
		for (final Project project : projects) {
			super.request("/manager/project/publish", "id=" + project.getId());
			super.checkPanicExists();
		}
		super.signOut();
	}
}
