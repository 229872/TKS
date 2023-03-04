package pl.lodz.p.edu.adapter.rest.api;

import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import java.util.List;
import java.util.UUID;

public interface EquipmentService {

    void add(EquipmentDTO equipment);

    List<EquipmentDTO> getAll();

    EquipmentDTO get(UUID uuid);

    void update(UUID entityId, EquipmentDTO equipmentDTO) throws RestIllegalModificationException;

    void remove(UUID uuid) throws RestConflictException;
}
