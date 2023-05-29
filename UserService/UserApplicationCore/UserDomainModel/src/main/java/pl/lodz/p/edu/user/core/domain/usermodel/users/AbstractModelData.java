package pl.lodz.p.edu.user.core.domain.usermodel.users;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@AllArgsConstructor
public abstract class AbstractModelData implements Serializable {

    public AbstractModelData(UUID id) {
        entityId = id;
    }

    @Getter
    private final UUID entityId;

    @Getter
    private Long version;
}
