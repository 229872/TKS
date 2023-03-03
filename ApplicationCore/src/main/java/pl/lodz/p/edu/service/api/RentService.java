package pl.lodz.p.edu.service.api;

import pl.lodz.p.edu.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.exception.ObjectNotValidException;
import pl.lodz.p.edu.DTO.RentDTO;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.model.Rent;
import pl.lodz.p.edu.model.users.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentService {
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
