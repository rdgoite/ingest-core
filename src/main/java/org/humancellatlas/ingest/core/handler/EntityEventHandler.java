package org.humancellatlas.ingest.core.handler;

import org.humancellatlas.ingest.core.AbstractEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by rolando on 06/09/2017.
 */
@Component
public class EntityEventHandler extends AbstractMongoEventListener<AbstractEntity> {

    @Override
    public void onBeforeSave(BeforeSaveEvent<AbstractEntity> entityBeforeSaveEvent) {
        entityBeforeSaveEvent.getSource().setUuid(UUID.randomUUID());
    }
}


