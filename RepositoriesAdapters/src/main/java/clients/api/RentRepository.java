package clients.api;

import clients.data.EquipmentEnt;
import clients.data.RentEnt;
import clients.data.users.ClientEnt;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.model.Rent;
import pl.lodz.p.edu.model.users.Client;

import java.util.List;

public interface RentRepository extends Repository<RentEnt> {
    List<RentEnt> getRentByEq(EquipmentEnt equipment);

    List<RentEnt> getRentByClient(ClientEnt client);

    List<RentEnt> getEquipmentRents(EquipmentEnt equipment);

    @Override
    void update(RentEnt elem);
}
