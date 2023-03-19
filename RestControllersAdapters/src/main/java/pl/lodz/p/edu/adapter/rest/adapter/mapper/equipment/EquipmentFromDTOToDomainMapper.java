package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDTOToDomainMapper {
    public Equipment convertToDomainModel(EquipmentInputDTO equipmentInputDTO) {
        return new Equipment(
                equipmentInputDTO.getFirstDayCost(),
                equipmentInputDTO.getNextDaysCost(),
                equipmentInputDTO.getBail(),
                equipmentInputDTO.getName()
        );
    }
}
