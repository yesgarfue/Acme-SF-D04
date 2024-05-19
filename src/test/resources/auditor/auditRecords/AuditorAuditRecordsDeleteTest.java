
package acme.testing.auditor.auditRecords;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;

import acme.entities.audits.AuditRecords;
import acme.testing.TestHarness;

public class AuditorAuditRecordsDeleteTest extends TestHarness {
	// Internal data ----------------------------------------------------------

	@Autowired
	private AuditorAuditRecordsTestRepository repository;

	// Test methods -----------------------------------------------------------


	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecords/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String oldCode, final String newCode) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, oldCode);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.clickOnSubmit("Delete");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, newCode);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecords/delete-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final int recordIndex, final String code) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Delete");

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		final Collection<AuditRecords> auditRecords = this.repository.findAuditRecordsByAuditor("auditor1");

		super.checkLinkExists("Sign in");
		auditRecords.forEach(auditRecord -> {
			super.request("/auditor/auditRecords/delete", "id=" + auditRecord.getId());
		});
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		auditRecords.forEach(auditRecord -> {
			super.request("/auditor/auditRecords/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("auditor2", "auditor2");
		auditRecords.forEach(auditRecord -> {
			super.request("/auditor/auditRecords/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		auditRecords.forEach(auditRecord -> {
			super.request("/auditor/auditRecords/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		auditRecords.forEach(auditRecord -> {
			super.request("/auditor/auditRecords/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager1", "manager1");
		auditRecords.forEach(auditRecord -> {
			super.request("/manager/project/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		auditRecords.forEach(auditRecord -> {
			super.request("/manager/project/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		});
		super.signOut();

	}

	@Test
	public void test301Hacking() {

		final Collection<AuditRecords> auditRecords = this.repository.findAuditRecordsByAuditor("auditor1");
		super.checkLinkExists("Sign in");
		super.signIn("auditor1", "auditor1");
		for (final AuditRecords auditRecord : auditRecords) {
			super.request("/manager/project/delete", "id=" + auditRecord.getId());
			super.checkPanicExists();
		}
		super.signOut();
	}
}
