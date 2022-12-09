package pl.lodz.p.edu.data.model;

import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Embeddable
public abstract class AbstractEntity implements Serializable {

    @NotNull
    @Column(name = "entity_id", unique = true)
    private UUID entityId = UUID.randomUUID();

    @Version
    @NotNull
    @Column(name = "version")
    private long version;

    public AbstractEntity() {}

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
