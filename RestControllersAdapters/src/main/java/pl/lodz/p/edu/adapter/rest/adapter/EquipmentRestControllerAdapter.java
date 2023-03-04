package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EquipmentRestControllerAdapter implements EquipmentService {

    @Inject
    private EquipmentServicePort servicePort;

    @Inject
    private EquipmentFromDomainToDTOMapper toDTOMapper;

    @Inject
    private EquipmentFromDTOToDomainMapper toDomainMapper;



    @Override
    public void add(EquipmentDTO equipmentDTO) {
        Equipment equipment = convertToDomainModel(equipmentDTO);
        servicePort.add(equipment);
    }
    @Override
    public List<EquipmentDTO> getAll() {
        List<Equipment> equipmentList = servicePort.getAll();
        return equipmentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EquipmentDTO get(UUID uuid) {
        Equipment equipment = servicePort.get(uuid);
        return convertToDTO(equipment);
    }

    @Override
    public void update(UUID entityId, EquipmentDTO equipmentDTO) throws RestIllegalModificationException {
        Equipment equipment = convertToDomainModel(equipmentDTO);
        try {
            servicePort.update(entityId, equipment);
        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void remove(UUID uuid) throws RestConflictException {
        try {
            servicePort.remove(uuid);
        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        }
    }

    private EquipmentDTO convertToDTO(Equipment equipment) {
        return toDTOMapper.convertToDTO(equipment);
    }

    private Equipment convertToDomainModel(EquipmentDTO equipmentDTO) {
        return toDomainMapper.convertToDomainModel(equipmentDTO);
    }
}
