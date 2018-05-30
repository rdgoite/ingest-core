package org.humancellatlas.ingest.submission.manifest;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by rolando on 30/05/2018.
 */
@CrossOrigin
public interface SubmissionManifestRepository extends MongoRepository<SubmissionManifest, String> {

}
