package pl.lodz.p.edu.core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public abstract class AbstractModelData implements Serializable {

    @Getter
    protected UUID entityId;
}
