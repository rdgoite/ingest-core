package org.humancellatlas.ingest.core.web;

/**
 * Enumerates the relations that are available in this application to provide some stability across different
 * implementations. These should not change without serious motivation, as will likely cause backwards-compatibility
 * breaking changes for clients.
 *
 * @author Tony Burdett
 * @date 05/09/17
 */
public class Links {
    // Links to request state changes for submission envelopes

    public static final String UPDATE_SUBMISSION_URL = "/updateSubmissions";
    public static final String UPDATE_SUBMISSION_REL = "updateSubmissions";

    public static final String SUBMIT_URL = "/submissionEvent";
    public static final String SUBMIT_REL = "submit";

    public static final String DRAFT_REL = "draft";
    public static final String DRAFT_URL = "/draftEvent";
    public static final String VALIDATING_REL = "validating";
    public static final String VALIDATING_URL = "/validatingEvent";
    public static final String VALID_REL = "valid";
    public static final String VALID_URL = "/validEvent";
    public static final String INVALID_REL = "invalid";
    public static final String INVALID_URL = "/invalidEvent";
    public static final String PROCESSING_REL ="processing";
    public static final String PROCESSING_URL ="/processingEvent";
    public static final String ARCHIVING_REL = "archiving";
    public static final String ARCHIVING_URL = "/archivingEvent";
    public static final String ARCHIVED_REL = "archived";
    public static final String ARCHIVED_URL = "/archivedEvent";
    public static final String EXPORT_REL = "export";
    public static final String EXPORT_URL = "/exportEvent";
    public static final String EXPORTED_REL = "exported";
    public static final String EXPORTED_URL = "/exportedEvent";
    public static final String CLEANUP_REL = "cleanup";
    public static final String CLEANUP_URL = "/cleanupEvent";
    public static final String COMPLETE_REL = "complete";
    public static final String COMPLETE_URL = "/completionEvent";

    // links to commit state changes
    public static final String COMMIT_SUBMIT_URL = "/commitSubmissionEvent";
    public static final String COMMIT_SUBMIT_REL = "commitSubmit";

    public static final String COMMIT_DRAFT_REL = "commitDraft";
    public static final String COMMIT_DRAFT_URL = "/commitDraftEvent";
    public static final String COMMIT_VALIDATING_REL = "commitValidating";
    public static final String COMMIT_VALIDATING_URL = "/commitValidatingEvent";
    public static final String COMMIT_VALID_REL = "commitValid";
    public static final String COMMIT_VALID_URL = "/commitValidEvent";
    public static final String COMMIT_INVALID_REL = "commitInvalid";
    public static final String COMMIT_INVALID_URL = "/commitInvalidEvent";
    public static final String COMMIT_PROCESSING_REL ="commitProcessing";
    public static final String COMMIT_PROCESSING_URL ="/commitProcessingEvent";
    public static final String COMMIT_ARCHIVING_REL = "commitArchiving";
    public static final String COMMIT_ARCHIVING_URL = "/commitArchivingEvent";
    public static final String COMMIT_ARCHIVED_REL = "commitArchived";
    public static final String COMMIT_ARCHIVED_URL = "/commitArchivedEvent";
    public static final String COMMIT_EXPORTING_REL = "commitExporting";
    public static final String COMMIT_EXPORTING_URL = "/commitExportingEvent";
    public static final String COMMIT_EXPORTED_REL = "commitExported";
    public static final String COMMIT_EXPORTED_URL = "/commitExportedEvent";
    public static final String COMMIT_CLEANUP_REL = "commitCleanup";
    public static final String COMMIT_CLEANUP_URL = "/commitCleanupEvent";
    public static final String COMMIT_COMPLETE_REL = "commitComplete";
    public static final String COMMIT_COMPLETE_URL = "/commitCompleteEvent";


    // Links to entities for submission envelopes
    public static final String BIOMATERIALS_URL = "/biomaterials";
    public static final String BIOMATERIALS_REL = "biomaterials";
    public static final String PROCESSES_URL = "/processes";
    public static final String PROCESSES_REL = "processes";
    public static final String FILES_URL = "/files";
    public static final String FILES_REL = "files";
    public static final String PROJECTS_URL = "/projects";
    public static final String PROJECTS_REL = "projects";
    public static final String PROTOCOLS_URL = "/protocols";
    public static final String PROTOCOLS_REL = "protocols";
    public static final String ASSAYS_URL = "/assays";
    public static final String ASSAYS_REL = "assays";
    public static final String ANALYSES_URL = "/analyses";
    public static final String ANALYSES_REL = "analyses";
    public static final String BUNDLE_MANIFESTS_URL = "/bundleManifests";
    public static final String BUNDLE_MANIFESTS_REL = "bundleManifests";
    public static final String SUBMISSION_MANIFEST_URL = "/submissionManifest";
    public static final String SUBMISSION_MANIFEST_REL = "submissionManifest";
    public static final String SUBMISSION_ERRORS_URL = "/submissionErrors";
    public static final String SUBMISSION_ERRORS_REL = "submissionEnvelopeErrors";
    public static final String SUBMISSION_DOCUMENTS_SM_URL = "/documentSmReport";
    public static final String SUBMISSION_DOCUMENTS_SM_REL = "documentSmReport";

    // Links for analyses
    public static final String BUNDLE_REF_URL = "/bundleReferences";
    public static final String BUNDLE_REF_REL = "inputBundleReferences";
    public static final String BUNDLE_REF_OLD_EVIL_REL = "add-input-bundles";
    public static final String FILE_REF_URL = "/fileReference";
    public static final String FILE_REF_REL = "inputFileReferences";
    public static final String FILE_REF_OLD_EVIL_REL = "add-file-reference";

    // Links to Processes
    public static final String INPUT_TO_PROCESSES_URL = "/inputToProcesses";
    public static final String INPUT_TO_PROCESSES_REL = "inputToProcesses";
    public static final String DERIVED_BY_PROCESSES_URL = "/derivedByProcesses";
    public static final String DERIVED_BY_PROCESSES_REL = "derivedByProcesses";

    // Links from Processes
    public static final String INPUT_BIOMATERIALS_URL = "/inputBiomaterials";
    public static final String INPUT_BIOMATERIALS_REL = "inputBiomaterials";
    public static final String INPUT_FILES_URL = "/inputFiles";
    public static final String INPUT_FILES_REL = "inputFiles";

    public static final String DERIVED_BY_BIOMATERIALS_URL = "/derivedBiomaterials";
    public static final String DERIVED_BY_BIOMATERIALS_REL = "derivedBiomaterials";
    public static final String DERIVED_BY_FILES_URL = "/derivedFiles";
    public static final String DERIVED_BY_FILES_REL = "derivedFiles";

    // Links from Files
    public static final String FILE_VALIDATION_JOB_URL = "/validationJob";
    public static final String FILE_VALIDATION_JOB_REL = "validationJob";

    // Links from StagingJobs
    public static final String COMPLETE_STAGING_JOB_URL = "/complete";
    public static final String COMPLETE_STAGING_JOB_REL = "completeStagingJob";

    // Links to ExportJobs
    public static final String EXPORT_JOBS_URL = "/exportJobs";
    public static final String EXPORT_JOBS_REL = "exportJobs";

    public static final String EXPORT_JOB_ENTITIES_URL = "/entities";
    public static final String EXPORT_JOB_ENTITIES_REL = "exportEntities";
    public static final String EXPORT_JOB_ENTITIES_BY_STATUS_REL = "exportEntitiesByStatus";

    public static final String EXPORT_JOB_FIND_URL = "/find";
    public static final String EXPORT_JOB_FIND_REL = "find";


}
