package pl.lodz.p.edu.adapter.rest.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDomainToDTOMapper;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EquipmentRestControllerAdapter implements EquipmentService {

    @Inject
    private EquipmentServicePort servicePort;

    @Inject
    private EquipmentFromDomainToDTOMapper toDTOMapper;

    @Inject
    private EquipmentFromDTOToDomainMapper toDomainMapper;


    @Override
    public EquipmentInputDTO add(EquipmentInputDTO equipmentInputDTO) {
        Equipment equipment = convertToDomainModel(equipmentInputDTO);
        return convertToDTO(servicePort.add(equipment));
    }
    @Override
    public List<EquipmentInputDTO> getAll() {
        List<Equipment> equipmentList = servicePort.getAll();
        return equipmentList.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EquipmentInputDTO get(UUID uuid) throws ObjectNotFoundRestException {
        try {
            Equipment equipment = servicePort.get(uuid);
            return convertToDTO(equipment);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public EquipmentInputDTO update(UUID entityId, EquipmentInputDTO equipmentInputDTO) throws RestIllegalModificationException,
            ObjectNotFoundRestException {
        Equipment equipment = convertToDomainModel(equipmentInputDTO);
        try {
            Equipment updated = servicePort.update(entityId, equipment);
            return convertToDTO(updated);

        } catch (IllegalModificationException e) {
            throw new RestIllegalModificationException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void remove(UUID uuid) throws RestConflictException, ObjectNotFoundRestException {
        try {
            servicePort.remove(uuid);

        } catch (ConflictException e) {
            throw new RestConflictException(e.getMessage(), e.getCause());
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundRestException(e.getMessage(), e.getCause());
        }
    }

    private EquipmentInputDTO convertToDTO(Equipment equipment) {
        return toDTOMapper.convertToDTO(equipment);
    }

    private Equipment convertToDomainModel(EquipmentInputDTO equipmentInputDTO) {
        return toDomainMapper.convertToDomainModel(equipmentInputDTO);
    }
}
