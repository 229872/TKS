package pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment;

import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDTOToDomainMapper {
    public Equipment convertToDomainModelFromInputDTO(EquipmentInputDTO equipmentInputDTO) {
        return new Equipment(
                equipmentInputDTO.getFirstDayCost(),
                equipmentInputDTO.getNextDaysCost(),
                equipmentInputDTO.getBail(),
                equipmentInputDTO.getName()
        );
    }

    public Equipment convertToDomainModelFromOutputDTO(EquipmentOutputDTO equipmentOutputDTO) {
        return new Equipment(
                equipmentOutputDTO.getEntityId(),
                equipmentOutputDTO.getFirstDayCost(),
                equipmentOutputDTO.getNextDaysCost(),
                equipmentOutputDTO.getBail(),
                equipmentOutputDTO.getName()
        );
    }
}
