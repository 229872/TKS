package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDTOToDomainMapper {
    public Equipment convertToDomainModel(EquipmentDTO equipmentDTO) {
        return new Equipment(
                equipmentDTO.getFirstDayCost(),
                equipmentDTO.getNextDaysCost(),
                equipmentDTO.getBail(),
                equipmentDTO.getName()
        );
    }
}
