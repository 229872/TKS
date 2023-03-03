package pl.lodz.p.edu.model;


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
