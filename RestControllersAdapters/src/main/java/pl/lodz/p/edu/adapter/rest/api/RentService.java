package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentService {
    RentDTO add(RentDTO rentDTO) throws RestObjectNotValidException, RestBusinessLogicInterruptException;

    List<RentDTO> getRentsByClient(ClientDTO client);

    List<RentDTO> getRentByEq(EquipmentDTO equipment);

    List<RentDTO> getAll();

    RentDTO get(UUID uuid);

    RentDTO update(UUID entityId, RentDTO rentDTO) throws RestObjectNotValidException,
            RestBusinessLogicInterruptException;

    void remove(UUID rentUuid) throws RestBusinessLogicInterruptException;

    boolean checkEquipmentAvailable(EquipmentDTO equipment, LocalDateTime now);
}
