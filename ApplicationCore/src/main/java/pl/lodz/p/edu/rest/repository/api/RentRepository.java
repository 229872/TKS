package pl.lodz.p.edu.rest.repository.api;

import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Client;

import java.util.List;

public interface RentRepository extends Repository<Rent> {
    List<Rent> getRentByEq(Equipment equipment);

    List<Rent> getRentByClient(Client client);

    List<Rent> getEquipmentRents(Equipment equipment);

    @Override
    void update(Rent elem);
}
