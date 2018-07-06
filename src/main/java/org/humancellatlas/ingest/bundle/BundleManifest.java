package org.humancellatlas.ingest.bundle;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.humancellatlas.ingest.core.*;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.Identifiable;

import java.util.*;

/**
 * Created by rolando on 05/09/2017.
 */
@AllArgsConstructor
@Getter
public class BundleManifest implements Identifiable<String> {
    private @Id @JsonIgnore String id;

    private final String bundleUuid;
    private final String envelopeUuid;

    private final List<String> dataFiles = new ArrayList();
    private final Map<String, Collection<String>> fileBiomaterialMap = new HashMap<>();
    private final Map<String, Collection<String>> fileProcessMap = new HashMap<>();
    private final Map<String, Collection<String>> fileProjectMap = new HashMap<>();
    private final Map<String, Collection<String>> fileProtocolMap = new HashMap<>();
    private final Map<String, Collection<String>> fileFilesMap = new HashMap<>();

}
