package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment;

import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.core.domain.model.Equipment;

public class EquipmentFromDomainToDataMapper {

    public EquipmentEnt convertToDataModel(Equipment equipment) {
        return new EquipmentEnt(
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getBail(),
                equipment.getName()
        );
    }
}
