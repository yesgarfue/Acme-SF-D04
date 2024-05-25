
package acme.features.any.auditRecords;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.client.data.accounts.Any;
import acme.entities.audits.AuditRecords;

@Controller
public class AnyAuditRecordsController extends AbstractController<Any, AuditRecords> {

	@Autowired
	private AnyAuditRecordsListService	listService;

	@Autowired
	private AnyAuditRecordsShowService	showService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
	}

}
