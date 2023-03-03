package clients.adapters.mapper.equipment;

import clients.data.EquipmentEnt;
import pl.lodz.p.edu.model.Equipment;

public class EquipmentFromDataToDomainMapper {

    public Equipment convertToDomainModel(EquipmentEnt equipmentEnt) {
        return new Equipment(
                equipmentEnt.getFirstDayCost(),
                equipmentEnt.getNextDaysCost(),
                equipmentEnt.getBail(),
                equipmentEnt.getName()
        );
    }
}
