
package acme.testing.auditor.auditRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditRecords;
import acme.testing.TestHarness;

public class AuditorAuditRecordsPublishTest extends TestHarness {
	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordsTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecords/publish-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
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

	public void test200Negative() {

	}

	@ParameterizedTest //Mismo csv que el 200Negative delete. Proyecto en modo draft-mode false
	@CsvFileSource(resources = "/auditor/auditRecords/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test201Negative(final int recordIndex, final String code) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
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

		final Collection<AuditRecords> auditRecords = this.repository.findAuditRecordsByAuditor("auditor1");

		super.checkLinkExists("Sign in");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager1", "manager1");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor2", "auditor2");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		auditRecords.forEach(project -> {
			super.request("/auditor/auditRecords/publish", "id=" + project.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

	@Test
	public void test301Hacking() {

		final Collection<AuditRecords> auditRecords = this.repository.findAuditRecordsNotInDraftModeByManager("auditor2");
		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		for (final AuditRecords auditRecord : auditRecords) {
			super.request("/auditor/auditRecords/publish", "id=" + auditRecord.getId());
			super.checkPanicExists();
		}
		super.signOut();
	}
}
