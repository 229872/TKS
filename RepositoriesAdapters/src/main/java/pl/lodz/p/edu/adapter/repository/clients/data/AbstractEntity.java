package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
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
