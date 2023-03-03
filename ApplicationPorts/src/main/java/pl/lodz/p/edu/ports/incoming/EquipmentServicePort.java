package pl.lodz.p.edu.ports.incoming;


import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.Equipment;

import java.util.List;
import java.util.UUID;

public interface EquipmentServicePort {

    void add(Equipment equipment);

    List<Equipment> getAll();

    Equipment get(UUID uuid);

    void update(UUID entityId, Equipment equipment) throws IllegalModificationException;

    void remove(UUID uuid) throws ConflictException;
}
