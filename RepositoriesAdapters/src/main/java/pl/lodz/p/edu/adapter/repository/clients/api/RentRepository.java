package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;

import java.util.List;

public interface RentRepository extends Repository<RentEnt> {
    List<RentEnt> getRentByEq(EquipmentEnt equipment);

    List<RentEnt> getRentByClient(ClientEnt client);

    List<RentEnt> getEquipmentRents(EquipmentEnt equipment);

    @Override
    void update(RentEnt elem);
}
