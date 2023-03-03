package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;

import java.util.UUID;

public interface EquipmentRepository extends Repository<EquipmentEnt> {
    boolean isEquipmentRented(UUID uuid);
}
