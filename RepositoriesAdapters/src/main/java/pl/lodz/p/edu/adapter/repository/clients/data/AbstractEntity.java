package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID entityId;

    @Version
    private long version;
}
