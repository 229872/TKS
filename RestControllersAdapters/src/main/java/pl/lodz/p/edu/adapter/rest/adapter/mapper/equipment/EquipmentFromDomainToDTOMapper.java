package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDomainToDTOMapper {

    public EquipmentDTO convertToDTO(Equipment equipment) {
        return new EquipmentDTO(
                equipment.getName(),
                equipment.getBail(),
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getDescription()
        );
    }
}
