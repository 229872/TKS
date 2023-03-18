package pl.lodz.p.edu.core.domain.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
public abstract class AbstractModelData implements Serializable {

    @Getter
    private final UUID entityId;
}
