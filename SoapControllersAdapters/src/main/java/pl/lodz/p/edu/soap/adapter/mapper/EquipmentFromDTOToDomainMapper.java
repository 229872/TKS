package pl.lodz.p.edu.soap.adapter.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.soap.dto.EquipmentInputSoapDTO;
import pl.lodz.p.edu.soap.dto.EquipmentOutputSoapDTO;

@ApplicationScoped
public class EquipmentFromDTOToDomainMapper {
    public Equipment convertToDomainModelFromInputDTO(EquipmentInputSoapDTO equipmentInputDTO) {
        return new Equipment(
                equipmentInputDTO.getFirstDayCost(),
                equipmentInputDTO.getNextDaysCost(),
                equipmentInputDTO.getBail(),
                equipmentInputDTO.getName()
        );
    }

    public Equipment convertToDomainModelFromOutputDTO(EquipmentOutputSoapDTO equipmentOutputDTO) {
        return new Equipment(
                equipmentOutputDTO.getEntityId(),
                equipmentOutputDTO.getFirstDayCost(),
                equipmentOutputDTO.getNextDaysCost(),
                equipmentOutputDTO.getBail(),
                equipmentOutputDTO.getName()
        );
    }
}
