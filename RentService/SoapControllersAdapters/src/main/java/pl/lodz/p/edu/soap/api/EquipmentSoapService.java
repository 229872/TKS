package pl.lodz.p.edu.soap.api;

import pl.lodz.p.edu.soap.dto.EquipmentInputSoapDTO;
import pl.lodz.p.edu.soap.dto.EquipmentOutputSoapDTO;
import pl.lodz.p.edu.soap.exception.ObjectNotFoundSoapException;
import pl.lodz.p.edu.soap.exception.SoapConflictException;
import pl.lodz.p.edu.soap.exception.SoapIllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface EquipmentSoapService {

    EquipmentOutputSoapDTO add(EquipmentInputSoapDTO equipment);

    List<EquipmentOutputSoapDTO> getAll();

    EquipmentOutputSoapDTO get(UUID uuid) throws ObjectNotFoundSoapException;

    EquipmentOutputSoapDTO update(UUID entityId, EquipmentInputSoapDTO equipmentInputDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException;

    void remove(UUID uuid) throws SoapConflictException, ObjectNotFoundSoapException;
}
