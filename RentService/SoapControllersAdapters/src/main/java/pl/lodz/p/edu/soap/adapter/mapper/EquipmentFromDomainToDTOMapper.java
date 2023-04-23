package pl.lodz.p.edu.soap.adapter.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.soap.dto.EquipmentInputSoapDTO;
import pl.lodz.p.edu.soap.dto.EquipmentOutputSoapDTO;

@ApplicationScoped
public class EquipmentFromDomainToDTOMapper {

    public EquipmentInputSoapDTO convertToInputDTO(Equipment equipment) {
        return new EquipmentInputSoapDTO(
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
    public EquipmentOutputSoapDTO convertToOutputDTO(Equipment equipment) {
        return new EquipmentOutputSoapDTO(
                equipment.getEntityId(),
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
}
