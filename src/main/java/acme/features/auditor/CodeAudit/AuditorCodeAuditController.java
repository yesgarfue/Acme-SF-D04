
package acme.features.auditor.CodeAudit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.audits.CodeAudit;
import acme.roles.Auditor;

@Controller
public class AuditorCodeAuditController extends AbstractController<Auditor, CodeAudit> {

	@Autowired
	private AuditorCodeAuditListService listService;

	/*
	 * @Autowired
	 * 
	 * protected AuditorAuditShowService showService;
	 * 
	 * @Autowired
	 * protected AuditorAuditCreateService createService;
	 * 
	 * @Autowired
	 * protected AuditorAuditUpdateService updateService;
	 * 
	 * @Autowired
	 * protected AuditorAuditDeleteService deleteService;
	 * 
	 * @Autowired
	 * protected AuditorAuditPublishService publishService;
	 */


	@PostConstruct
	private void initialise() {
		super.addBasicCommand("list", this.listService);
		/*
		 * super.addBasicCommand("show", this.showService);
		 * super.addBasicCommand("create", this.createService);
		 * super.addBasicCommand("update", this.updateService);
		 * super.addBasicCommand("delete", this.deleteService);
		 * super.addCustomCommand("publish", "update", this.publishService);
		 */

	}
}
