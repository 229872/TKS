package pl.lodz.p.edu.ports.incoming;



import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentServicePort {
    Rent add(Rent rent) throws ObjectNotValidException, BusinessLogicInterruptException, ObjectNotFoundServiceException;

    List<Rent> getRentsByClient(Client client);

    List<Rent> getRentsByEquipment(Equipment equipment);

    List<Rent> getAll();

    Rent get(UUID uuid) throws ObjectNotFoundServiceException;

    Rent update(UUID uuid, Rent rent) throws ObjectNotValidException,
            BusinessLogicInterruptException, ObjectNotFoundServiceException;

    void remove(UUID uuid) throws BusinessLogicInterruptException, ObjectNotFoundServiceException;

    boolean checkEquipmentAvailable(Equipment equipment, LocalDateTime now);
}
