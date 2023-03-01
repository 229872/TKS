package pl.lodz.p.edu.outcoming;

import pl.lodz.p.edu.rest.model.Equipment;

import java.util.UUID;

public interface EquipmentRepository extends Repository<Equipment> {
    boolean isEquipmentRented(UUID uuid);
}
