package pl.lodz.p.edu.ports.incoming;


import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Equipment;

import java.util.List;
import java.util.UUID;

public interface EquipmentServicePort {

    Equipment add(Equipment equipment);

    List<Equipment> getAll();

    Equipment get(UUID uuid) throws ObjectNotFoundServiceException;

    Equipment update(UUID uuid, Equipment equipment) throws IllegalModificationException, ObjectNotFoundServiceException;

    void remove(UUID uuid) throws ConflictException, ObjectNotFoundServiceException;
}
