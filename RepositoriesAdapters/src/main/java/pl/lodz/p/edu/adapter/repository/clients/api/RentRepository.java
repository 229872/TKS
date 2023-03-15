package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;

import java.util.List;

public interface RentRepository extends Repository<RentEnt> {
    List<RentEnt> getRentsByEquipment(EquipmentEnt equipment);
    List<RentEnt> getRentsByClient(ClientEnt client);
    @Override
    RentEnt update(RentEnt elem);
}
