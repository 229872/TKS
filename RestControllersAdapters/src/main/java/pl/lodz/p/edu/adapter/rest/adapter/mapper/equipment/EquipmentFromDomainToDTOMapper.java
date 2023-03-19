package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDomainToDTOMapper {

    public EquipmentInputDTO convertToDTO(Equipment equipment) {
        return new EquipmentInputDTO(
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
}
