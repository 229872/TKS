package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDataToDomainMapper {

    public Equipment convertToDomainModel(EquipmentEnt equipmentEnt) {
        return new Equipment(
                equipmentEnt.getEntityId(),
                equipmentEnt.getFirstDayCost(),
                equipmentEnt.getNextDaysCost(),
                equipmentEnt.getBail(),
                equipmentEnt.getName()
        );
    }
}
