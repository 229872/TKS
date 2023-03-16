package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentService {
    RentDTO add(RentDTO rentDTO) throws RestObjectNotValidException, RestBusinessLogicInterruptException, ObjectNotFoundRestException;

    List<RentDTO> getRentsByClient(ClientDTO client);

    List<RentDTO> getRentsByEquipment(EquipmentDTO equipment);

    List<RentDTO> getAll();

    RentDTO get(UUID uuid) throws ObjectNotFoundRestException;

    RentDTO update(UUID entityId, RentDTO rentDTO) throws RestObjectNotValidException,
            RestBusinessLogicInterruptException, ObjectNotFoundRestException;

    void remove(UUID rentUuid) throws RestBusinessLogicInterruptException, ObjectNotFoundRestException;

    boolean checkEquipmentAvailable(EquipmentDTO equipment, LocalDateTime now);
}
