package pl.lodz.p.edu.service.api;

import pl.lodz.p.edu.exception.ConflictException;
import pl.lodz.p.edu.exception.IllegalModificationException;
import pl.lodz.p.edu.DTO.EquipmentDTO;
import pl.lodz.p.edu.model.Equipment;

import java.util.List;
import java.util.UUID;

public interface EquipmentService {

    void add(Equipment equipment);

    List<Equipment> getAll();

    Equipment get(UUID uuid);

    void update(UUID entityId, EquipmentDTO equipmentDTO) throws IllegalModificationException;

    void remove(UUID uuid) throws ConflictException;
}
