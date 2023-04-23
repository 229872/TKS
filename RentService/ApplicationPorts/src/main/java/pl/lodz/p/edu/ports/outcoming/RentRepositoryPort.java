package pl.lodz.p.edu.ports.outcoming;

import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;

import java.util.List;
import java.util.UUID;

public interface RentRepositoryPort extends RepositoryPort<Rent> {
    List<Rent> getRentsByEquipment(Equipment equipment);

    List<Rent> getRentsByClient(UUID clientUuid);

    @Override
    Rent update(Rent object);

    List<Rent> getEquipmentRents(Equipment equipment);
}
