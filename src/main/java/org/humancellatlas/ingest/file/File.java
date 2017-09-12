package org.humancellatlas.ingest.file;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.humancellatlas.ingest.core.*;
import org.humancellatlas.ingest.envelope.SubmissionEnvelope;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Getter
@Setter
public class File extends MetadataDocument {
    private @DBRef SubmissionEnvelope submissionEnvelope;

    private String fileName;
    private String cloudUrl;
    private Checksums checksums;

    protected File() {
        super(EntityType.FILE, null, new SubmissionDate(new Date()), new UpdateDate(new Date()), null, ValidationStatus.PENDING, new ValidationChecksum());
        this.submissionEnvelope = null;
        this.cloudUrl = "";
        this.fileName = "";
        this.checksums = null;
    }

    protected File(EntityType type,
                   Uuid uuid,
                   SubmissionDate submissionDate,
                   UpdateDate updateDate,
                   SubmissionEnvelope submissionEnvelope,
                   String fileName,
                   String cloudUrl,
                   Checksums checksums,
                   Object content,
                   ValidationStatus validationStatus,
                   ValidationChecksum validationChecksum) {
        super(type, uuid, submissionDate, updateDate, content, validationStatus, validationChecksum);
        this.submissionEnvelope = submissionEnvelope;
        this.fileName = fileName;
        this.cloudUrl = cloudUrl;
        this.checksums = checksums;
    }

    @JsonCreator
    protected File(@JsonProperty("fileName") String fileName,
                   @JsonProperty("content") Object content) {
        this(EntityType.FILE,
             null,
             new SubmissionDate(new Date()),
             new UpdateDate(new Date()),
             null,
             fileName,
             "",
             null,
             content,
                ValidationStatus.PENDING,
                new ValidationChecksum());
    }

    public File addToSubmissionEnvelope(SubmissionEnvelope submissionEnvelope) {
        this.submissionEnvelope = submissionEnvelope;

        return this;
    }

    public boolean isInEnvelope(SubmissionEnvelope submissionEnvelope) {
        return this.submissionEnvelope.equals(submissionEnvelope);
    }

    public boolean isInEnvelopeWithUuid(Uuid uuid) {
        return this.submissionEnvelope.getUuid().equals(uuid);
    }
}
