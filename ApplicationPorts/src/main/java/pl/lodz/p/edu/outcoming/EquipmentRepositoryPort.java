package pl.lodz.p.edu.outcoming;

import pl.lodz.p.edu.model.Equipment;

import java.util.UUID;

public interface EquipmentRepositoryPort extends RepositoryPort<Equipment> {
    boolean isEquipmentRented(UUID uuid);
}
