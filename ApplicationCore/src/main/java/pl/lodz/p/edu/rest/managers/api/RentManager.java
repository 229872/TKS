package pl.lodz.p.edu.rest.managers.api;

import pl.lodz.p.edu.rest.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentManager {
    Rent add(RentDTO rentDTO) throws ObjectNotValidException, BusinessLogicInterruptException;

    List<Rent> getRentsByClient(Client client);

    List<Rent> getRentByEq(Equipment equipment);

    List<Rent> getAll();

    Rent get(UUID uuid);

    Rent update(UUID entityId, RentDTO rentDTO) throws ObjectNotValidException,
            BusinessLogicInterruptException;

    void remove(UUID rentUuid) throws BusinessLogicInterruptException;

    boolean checkEquipmentAvailable(Equipment equipment, LocalDateTime now);
}
