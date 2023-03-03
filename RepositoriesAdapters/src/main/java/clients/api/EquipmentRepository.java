package clients.api;

import clients.data.EquipmentEnt;
import pl.lodz.p.edu.model.Equipment;

import java.util.UUID;

public interface EquipmentRepository extends Repository<EquipmentEnt> {
    boolean isEquipmentRented(UUID uuid);
}
