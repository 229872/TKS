package pl.lodz.p.edu.rest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;


public abstract class AbstractModelData implements Serializable {

    private UUID entityId = UUID.randomUUID();

    private long version;

    public AbstractModelData() {}

    public UUID getEntityId() {
        return entityId;
    }

    @Override
    public String toString() {
        return "AbstractEntity{" +
                "entityId=" + entityId +
                ", version=" + version +
                '}';
    }
}
