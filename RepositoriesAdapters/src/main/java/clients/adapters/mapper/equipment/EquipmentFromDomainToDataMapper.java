package clients.adapters.mapper.equipment;

import clients.data.EquipmentEnt;
import pl.lodz.p.edu.model.Equipment;

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
