package pl.lodz.p.edu.ports.outgoing;

import java.util.UUID;
import pl.lodz.p.edu.core.domain.model.Equipment;

public interface EquipmentRepositoryPort extends RepositoryPort<Equipment> {
    boolean isEquipmentRented(UUID uuid);
}
