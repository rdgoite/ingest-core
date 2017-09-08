package org.humancellatlas.ingest.core;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Created by rolando on 07/09/2017.
 */
@Getter
public class BioMetadataDocument extends MetadataDocument {
    private @Setter ValidationStatus validationStatus;
    private final Accession accession;

    protected BioMetadataDocument(EntityType type,
                                  UUID uuid,
                                  SubmissionDate submissionDate,
                                  UpdateDate updateDate,
                                  Accession accession,
                                  Object content,
                                  ValidationStatus validationStatus) {
        super(type, uuid, submissionDate, updateDate, content);
        this.accession = accession;
        this.validationStatus = validationStatus;
    }
}
