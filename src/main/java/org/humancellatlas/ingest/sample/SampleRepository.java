package org.humancellatlas.ingest.sample;

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
public interface SampleRepository extends MongoRepository<Sample, String> {
    public Sample findByUuid(UUID uuid);

    public Page<Sample> findBySubmissionEnvelope(SubmissionEnvelope submissionEnvelope, Pageable pageable);
}
