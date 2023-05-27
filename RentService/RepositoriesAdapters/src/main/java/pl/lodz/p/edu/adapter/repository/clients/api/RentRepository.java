package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;

import java.util.List;
import java.util.UUID;

public interface RentRepository extends Repository<RentEnt> {
    List<RentEnt> getRentsByEquipment(EquipmentEnt equipment);
    List<RentEnt> getRentsByClient(UUID clientUuid);

    List<RentEnt> getEquipmentRents(EquipmentEnt equipment);
    @Override
    RentEnt update(RentEnt elem);
}
