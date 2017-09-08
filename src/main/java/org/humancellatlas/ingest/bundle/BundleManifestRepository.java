package org.humancellatlas.ingest.bundle;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Created by rolando on 05/09/2017.
 */
public interface BundleManifestRepository extends MongoRepository<BundleManifest, String> {
    BundleManifest findByBundleUuid(UUID uuid);
}
