
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.audits.AuditRecords;
import acme.entities.audits.CodeAudits;
import acme.entities.contract.Contract;
import acme.entities.contract.ProgressLog;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectUserStory;
import acme.entities.sponsor.Invoice;
import acme.entities.sponsor.Sponsorship;
import acme.entities.training.TrainingModule;
import acme.entities.training.TrainingSession;
import acme.enumerate.Priority;
import acme.roles.Manager;

public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT p FROM Project p WHERE p.manager.id = :managerId")
	Collection<Project> findManyProjectsByManagerId(int managerId);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT p FROM Project p WHERE p.code = :code")
	Project findProjectByCode(String code);

	@Query("SELECT pus FROM ProjectUserStory pus WHERE pus.project.id = :id")
	Collection<ProjectUserStory> findProjectUserStoriesByProject(int id);

	@Query("SELECT count(pus) FROM ProjectUserStory pus WHERE pus.userStory.priority = :priority AND pus.project.id = :id")
	Integer numOfUserStoriesOfOnePriorityByProject(int id, Priority priority);

	//VALIDATIONS
	@Query("SELECT COUNT(pus) > 0 FROM ProjectUserStory pus WHERE pus.userStory.draftMode = true  AND pus.project.id = :projectId")
	boolean findUserStoriesWithoutPublishByProject(int projectId);

	@Query("SELECT COUNT(p) > 0 FROM Project p WHERE p.indication = true AND p.id = :ProjectId")
	boolean hasAProjectAnyFatalError(int ProjectId);

	@Query("SELECT sc.aceptedCurrencies FROM SystemConfiguration sc")
	String getAvailableCurrencies();

	// DELETE ALL ENTITIES ASSOCIATED TO A PROJECT
	@Query("SELECT ca FROM CodeAudits ca WHERE ca.project.id = :projectId")
	Collection<CodeAudits> findCodeAuditsByProject(int projectId);

	@Query("SELECT ar FROM AuditRecords ar WHERE ar.codeAudit.id = :id")
	Collection<AuditRecords> findCodeAuditingRecordByCodeAudit(int id);
	@Query("SELECT c FROM Contract c WHERE c.project.id = :projectId")
	Collection<Contract> findContractsByProject(int projectId);

	@Query("SELECT pl FROM ProgressLog pl WHERE pl.contract.id = :contractId")
	Collection<ProgressLog> findProgressLogByContract(int contractId);

	@Query("SELECT s FROM Sponsorship s WHERE s.project.id = :projectId")
	Collection<Sponsorship> findSponsorShipsByProject(int projectId);

	@Query("SELECT i FROM Invoice i WHERE i.sponsorship.id = :sponsorshipId")
	Collection<Invoice> findInvoiceBySponsorship(int sponsorshipId);

	@Query("SELECT tm FROM TrainingModule tm WHERE tm.project.id = :projectId")
	Collection<TrainingModule> findTrainingModuleByProject(int projectId);

	@Query("SELECT ts FROM TrainingSession ts WHERE ts.trainingModule.id = :trainingModuleId")
	Collection<TrainingSession> TrainingSessionByTrainingModule(int trainingModuleId);

	//DELETE FUNCTION
	default void deleteAllEntitiesAssociatedToAProject(final Project object) {

		this.deleteAll(this.findProjectUserStoriesByProject(object.getId()));

		final Collection<CodeAudits> codeAudits = this.findCodeAuditsByProject(object.getId());
		for (final CodeAudits a : codeAudits)
			this.deleteAll(this.findCodeAuditingRecordByCodeAudit(a.getId()));
		this.deleteAll(codeAudits);
		final Collection<Contract> contracts = this.findContractsByProject(object.getId());
		for (final Contract c : contracts)
			this.deleteAll(this.findProgressLogByContract(c.getId()));
		this.deleteAll(contracts);

		final Collection<Sponsorship> sponsors = this.findSponsorShipsByProject(object.getId());
		for (final Sponsorship s : sponsors)
			this.deleteAll(this.findInvoiceBySponsorship(s.getId()));
		this.deleteAll(sponsors);

		final Collection<TrainingModule> trains = this.findTrainingModuleByProject(object.getId());
		for (final TrainingModule t : trains)
			this.deleteAll(this.TrainingSessionByTrainingModule(t.getId()));
		this.deleteAll(trains);

	}

}
