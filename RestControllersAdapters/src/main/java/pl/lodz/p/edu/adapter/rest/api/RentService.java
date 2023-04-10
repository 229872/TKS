package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.RentOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalDateException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RentService {
    void add(RentInputDTO rentInputDTO) throws RestObjectNotValidException, RestBusinessLogicInterruptException, ObjectNotFoundRestException, RestIllegalDateException;

    List<RentOutputDTO> getRentsByClientId(UUID clientUuid);

    List<RentInputDTO> getRentsByEquipment(EquipmentOutputDTO equipment);

    List<RentOutputDTO> getAll();

    RentOutputDTO get(UUID uuid) throws ObjectNotFoundRestException;

    RentInputDTO update(UUID entityId, RentInputDTO rentInputDTO) throws RestObjectNotValidException,
            RestBusinessLogicInterruptException, ObjectNotFoundRestException, RestIllegalDateException;

    void remove(UUID rentUuid) throws RestBusinessLogicInterruptException, ObjectNotFoundRestException;

    boolean checkEquipmentAvailable(EquipmentOutputDTO equipment, LocalDateTime now);
}
