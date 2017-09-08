package org.humancellatlas.ingest.project;

import org.humancellatlas.ingest.envelope.SubmissionEnvelope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Javadocs go here!
 *
 * @author Tony Burdett
 * @date 31/08/17
 */
public interface ProjectRepository extends MongoRepository<Project, String> {
    public Project findByUuid(UUID uuid);

    public Page<Project> findBySubmissionEnvelope(SubmissionEnvelope submissionEnvelope, Pageable pageable);
}
