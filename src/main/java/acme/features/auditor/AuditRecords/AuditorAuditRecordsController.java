
package acme.features.auditor.AuditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audits.AuditRecords;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordsController extends AbstractController<Auditor, AuditRecords> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordsListService		listService;

	@Autowired
	private AuditorAuditRecordsShowService		showService;

	@Autowired
	private AuditorAuditRecordsCreateService	createService;

	@Autowired
	private AuditorAuditRecordsUpdateService	updateService;

	/*
	 * @Autowired
	 * private AuditorAuditRecordsDeleteService deleteService;
	 * 
	 * @Autowired
	 * private AuditorAuditRecordsPublishService publishService;
	 */
	// Constructors -----------------------------------------------------------


	@PostConstruct
	private void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		/*
		 * super.addBasicCommand("delete", this.deleteService);
		 * super.addCustomCommand("publish", "update", this.publishService);
		 */
	}

}
