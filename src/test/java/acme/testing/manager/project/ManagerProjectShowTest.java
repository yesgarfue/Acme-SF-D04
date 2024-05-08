
package acme.testing.manager.project;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.projects.Project;
import acme.testing.TestHarness;

public class ManagerProjectShowTest extends TestHarness {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/manager/project/show-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String title, final String abstracts, final String indication, final String cost, final String link, final String draftMode) {

		super.signIn("manager1", "manager1");

		super.clickOnMenu("Manager", "Projects");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.clickOnListingRecord(recordIndex);

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("abstracts", abstracts);
		super.checkInputBoxHasValue("indication", indication);
		super.checkInputBoxHasValue("cost", cost);
		super.checkInputBoxHasValue("link", link);
		super.checkInputBoxHasValue("draftMode", draftMode);

		super.signOut();
	}

	@Test
	public void test200Negative() {
		// There are not any negative test cases for this feature.
	}

	@Test
	public void test300Hacking() {

		final Collection<Project> projects = this.repository.findProjectsByManager("manager1");

		super.checkLinkExists("Sign in");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager2", "manager2");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		projects.forEach(project -> {
			super.request("/manager/project/show", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();
	}
}
