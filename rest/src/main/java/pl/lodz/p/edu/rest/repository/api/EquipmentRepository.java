package pl.lodz.p.edu.rest.repository.api;

import pl.lodz.p.edu.data.model.Equipment;

import java.util.UUID;

public interface EquipmentRepository extends Repository<Equipment> {
    boolean isEquipmentRented(UUID uuid);
}
