package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface EquipmentService {

    EquipmentOutputDTO add(EquipmentInputDTO equipment);

    List<EquipmentOutputDTO> getAll();

    EquipmentOutputDTO get(UUID uuid) throws ObjectNotFoundRestException;

    EquipmentOutputDTO update(UUID entityId, EquipmentInputDTO equipmentInputDTO) throws RestIllegalModificationException, ObjectNotFoundRestException;

    void remove(UUID uuid) throws RestConflictException, ObjectNotFoundRestException;
}
