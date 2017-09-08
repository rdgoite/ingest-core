package org.humancellatlas.ingest.bundle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.humancellatlas.ingest.core.*;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rolando on 05/09/2017.
 */
@AllArgsConstructor
@Getter
public class BundleManifest implements Identifiable<String> {
    private @Id @JsonIgnore String id;

    private final UUID bundleUuid;

    private final List<UUID> fileUuids;
    private final Map<UUID, Collection<UUID>> fileSampleMap;
    private final Map<UUID, Collection<UUID>> fileAssayMap;
    private final Map<UUID, Collection<UUID>> fileAnalysisMap;
    private final Map<UUID, Collection<UUID>> fileProjectMap;
    private final Map<UUID, Collection<UUID>> fileProtocolMap;
}
