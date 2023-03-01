package pl.lodz.p.edu.rest.service.api;

import pl.lodz.p.edu.rest.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface EquipmentService {

    void add(Equipment equipment);

    List<Equipment> getAll();

    Equipment get(UUID uuid);

    void update(UUID entityId, EquipmentDTO equipmentDTO) throws IllegalModificationException;

    void remove(UUID uuid) throws ConflictException;
}
