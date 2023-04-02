package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDomainToDTOMapper {

    public EquipmentInputDTO convertToInputDTO(Equipment equipment) {
        return new EquipmentInputDTO(
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
    public EquipmentOutputDTO convertToOutputDTO(Equipment equipment) {
        return new EquipmentOutputDTO(
                equipment.getEntityId(),
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
}
