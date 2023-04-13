package pl.lodz.p.edu.soap.controller;

import jakarta.inject.Inject;
import jakarta.jws.WebService;
import pl.lodz.p.edu.soap.api.EquipmentSoapService;
import pl.lodz.p.edu.soap.dto.EquipmentInputSoapDTO;
import pl.lodz.p.edu.soap.dto.EquipmentOutputSoapDTO;
import pl.lodz.p.edu.soap.exception.ObjectNotFoundSoapException;
import pl.lodz.p.edu.soap.exception.SoapConflictException;
import pl.lodz.p.edu.soap.exception.SoapIllegalModificationException;

import java.util.List;
import java.util.UUID;

@WebService
public class EquipmentSoapController {
    @Inject
    private EquipmentSoapService equipmentSoapService;


    public EquipmentOutputSoapDTO add(EquipmentInputSoapDTO equipment) {
        return equipmentSoapService.add(equipment);
    }

    public List<EquipmentOutputSoapDTO> getAll() {
        return equipmentSoapService.getAll();
    }

    public EquipmentOutputSoapDTO get(UUID uuid) throws ObjectNotFoundSoapException {
        return equipmentSoapService.get(uuid);
    }

    public EquipmentOutputSoapDTO update(UUID entityId, EquipmentInputSoapDTO equipmentInputDTO) throws SoapIllegalModificationException, ObjectNotFoundSoapException {
        return equipmentSoapService.update(entityId, equipmentInputDTO);
    }

    public void remove(UUID uuid) throws SoapConflictException, ObjectNotFoundSoapException {
        equipmentSoapService.remove(uuid);
    }
}
