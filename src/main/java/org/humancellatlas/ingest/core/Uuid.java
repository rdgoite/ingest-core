package org.humancellatlas.ingest.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.UUID;

/**
 * Javadocs go here!
 *
 * @author Tony Burdett
 * @date 31/08/17
 */
@Data
public class Uuid {
    private String name;

    @JsonCreator
    public Uuid(String name) {
        // throws IllegalArgumentException if not valid
        this.name = UUID.fromString(name).toString();
    }

    public Uuid() {
        this.name = UUID.randomUUID().toString();
    }
}
