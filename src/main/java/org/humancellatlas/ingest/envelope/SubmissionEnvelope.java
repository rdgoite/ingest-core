package org.humancellatlas.ingest.envelope;

import lombok.Getter;
import lombok.Setter;
import org.humancellatlas.ingest.core.AbstractEntity;
import org.humancellatlas.ingest.core.EntityType;
import org.humancellatlas.ingest.core.SubmissionDate;
import org.humancellatlas.ingest.core.SubmissionStatus;
import org.humancellatlas.ingest.core.UpdateDate;

import java.util.Date;
import java.util.UUID;

/**
 * Javadocs go here!
 *
 * @author Tony Burdett
 * @date 30/08/17
 */
@Getter
public class SubmissionEnvelope extends AbstractEntity {
    private @Setter SubmissionStatus submissionStatus;

    public SubmissionEnvelope(UUID uuid,
                              SubmissionDate submissionDate,
                              UpdateDate updateDate,
                              SubmissionStatus submissionStatus) {
        super(EntityType.SUBMISSION, uuid, submissionDate, updateDate);
        this.submissionStatus = submissionStatus;
    }

    public SubmissionEnvelope() {
        this(null,
             new SubmissionDate(new Date()),
             new UpdateDate(new Date()),
             SubmissionStatus.DRAFT);
    }
}
