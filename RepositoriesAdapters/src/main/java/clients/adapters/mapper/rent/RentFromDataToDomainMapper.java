package clients.adapters.mapper.rent;

import clients.adapters.mapper.equipment.EquipmentFromDataToDomainMapper;
import clients.adapters.mapper.user.UserFromDataToDomainMapper;
import clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.model.Rent;

public class RentFromDataToDomainMapper {

    @Inject
    private EquipmentFromDataToDomainMapper equipmentToDomainMapper;

    @Inject
    private UserFromDataToDomainMapper userToDomainMapper;

    public Rent convertToDomainModel(RentEnt rentEnt) {
        return new Rent (
                rentEnt.getBeginTime(),
                rentEnt.getEndTime(),
                equipmentToDomainMapper.convertToDomainModel(
                        rentEnt.getEquipment()
                ),
                userToDomainMapper.convertClientToDomainModel(
                        rentEnt.getClient()
                )
        );
    }
}
