package pl.lodz.p.edu.soap.adapter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;
import pl.lodz.p.edu.soap.adapter.mapper.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.soap.adapter.mapper.EquipmentFromDomainToDTOMapper;
import pl.lodz.p.edu.soap.api.EquipmentSoapService;
import pl.lodz.p.edu.soap.dto.EquipmentInputSoapDTO;
import pl.lodz.p.edu.soap.dto.EquipmentOutputSoapDTO;
import pl.lodz.p.edu.soap.exception.ObjectNotFoundSoapException;
import pl.lodz.p.edu.soap.exception.SoapConflictException;
import pl.lodz.p.edu.soap.exception.SoapIllegalModificationException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EquipmentSoapControllerAdapter implements EquipmentSoapService {

    @Inject
    private EquipmentServicePort equipmentServicePort;

    @Inject
    private EquipmentFromDTOToDomainMapper toDomainMapper;

    @Inject
    private EquipmentFromDomainToDTOMapper toDTOMapper;

    @Override
    public EquipmentOutputSoapDTO add(EquipmentInputSoapDTO equipment) {
        Equipment eq = toDomainMapper.convertToDomainModelFromInputDTO(equipment);
        Equipment added = equipmentServicePort.add(eq);
        return toDTOMapper.convertToOutputDTO(added);
    }

    @Override
    public List<EquipmentOutputSoapDTO> getAll() {
        return equipmentServicePort.getAll().stream()
                .map(this::convertToOutputDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EquipmentOutputSoapDTO get(UUID uuid) throws ObjectNotFoundSoapException {
        try {
            Equipment equipment = equipmentServicePort.get(uuid);
            return convertToOutputDTO(equipment);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundSoapException(e.getMessage(), e);
        }
    }

    @Override
    public EquipmentOutputSoapDTO update(UUID entityId, EquipmentInputSoapDTO equipmentInputDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException {
        Equipment equipment = toDomainMapper.convertToDomainModelFromInputDTO(equipmentInputDTO);
        try {
            Equipment updated = equipmentServicePort.update(entityId, equipment);
            return toDTOMapper.convertToOutputDTO(updated);
        }  catch (IllegalModificationException e) {
            throw new SoapIllegalModificationException(e.getMessage(), e);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundSoapException(e.getMessage(), e);
        }
    }

    @Override
    public void remove(UUID uuid) throws SoapConflictException, ObjectNotFoundSoapException {
        try {
            equipmentServicePort.remove(uuid);
        } catch (ConflictException e) {
            throw new SoapConflictException(e.getMessage(), e);
        } catch (ObjectNotFoundServiceException e) {
            throw new ObjectNotFoundSoapException(e.getMessage(), e);
        }
    }

    private EquipmentOutputSoapDTO convertToOutputDTO(Equipment equipment) {
        return toDTOMapper.convertToOutputDTO(equipment);
    }
}
