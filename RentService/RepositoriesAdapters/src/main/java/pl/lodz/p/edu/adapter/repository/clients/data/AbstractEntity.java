package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    protected UUID entityId;

    @Version
    private long version;

    protected AbstractEntity(){};

}
