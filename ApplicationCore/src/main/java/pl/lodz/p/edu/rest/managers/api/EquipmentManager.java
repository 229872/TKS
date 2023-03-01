package pl.lodz.p.edu.rest.managers.api;

import pl.lodz.p.edu.rest.data.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.data.model.Equipment;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface EquipmentManager {

    void add(Equipment equipment);

    List<Equipment> getAll();

    Equipment get(UUID uuid);

    void update(UUID entityId, EquipmentDTO equipmentDTO) throws IllegalModificationException;

    void remove(UUID uuid) throws ConflictException;
}
