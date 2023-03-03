package pl.lodz.p.edu.ports.incoming;



import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentServicePort {
    Rent add(Rent rent) throws ObjectNotValidException, BusinessLogicInterruptException;

    List<Rent> getRentsByClient(Client client);

    List<Rent> getRentByEq(Equipment equipment);

    List<Rent> getAll();

    Rent get(UUID uuid);

    Rent update(UUID entityId, Rent rent) throws ObjectNotValidException,
            BusinessLogicInterruptException;

    void remove(UUID rentUuid) throws BusinessLogicInterruptException;

    boolean checkEquipmentAvailable(Equipment equipment, LocalDateTime now);
}
