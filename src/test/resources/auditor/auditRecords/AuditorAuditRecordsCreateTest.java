
package acme.testing.auditor.auditRecords;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AuditorAuditRecordsCreateTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecords/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test100Positive(final int recordIndex, final String code, final String startTime, final String finishTime, final String mark, final String optionalLink, final String codeAudit) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();
		super.clickOnButton("Create");
		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("finishTime", finishTime);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("optionalLink", optionalLink);
		super.fillInputBoxIn("codeAudit", codeAudit);
		super.clickOnSubmit("Create");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();
		super.sortListing(0, "asc");
		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, mark);
		super.checkColumnHasValue(recordIndex, 2, optionalLink);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("finishTime", finishTime);
		super.checkInputBoxHasValue("mark", mark);
		super.checkInputBoxHasValue("optionalLink", optionalLink);
		super.checkInputBoxHasValue("codeAudit", codeAudit);
		super.checkInputBoxHasValue("draftMode", "true");

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/auditor/auditRecords/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	public void test200Negative(final String code, final String startTime, final String finishTime, final String mark, final String optionalLink, final String codeAudit) {

		super.signIn("auditor1", "auditor1");

		super.clickOnMenu("Auditor", "Audit Records");
		super.checkListingExists();

		super.clickOnButton("Create");
		super.checkFormExists();
		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("finishTime", finishTime);
		super.fillInputBoxIn("mark", mark);
		super.fillInputBoxIn("optionalLink", optionalLink);
		super.clickOnSubmit("Create");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	public void test300Hacking() {

		super.checkLinkExists("Sign in");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();

		super.checkLinkExists("Sign in");
		super.signIn("administrator", "administrator");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("client1", "client1");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("manager1", "manager1");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("developer1", "developer1");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();
		super.signOut();

		super.checkLinkExists("Sign in");
		super.signIn("sponsor1", "sponsor1");
		super.request("/auditor/audit-records/create");
		super.checkPanicExists();
		super.signOut();
	}

}
