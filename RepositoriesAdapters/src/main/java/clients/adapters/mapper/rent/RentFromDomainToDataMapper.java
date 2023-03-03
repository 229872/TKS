package clients.adapters.mapper.rent;

import clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import clients.adapters.mapper.user.UserFromDomainToDataMapper;
import clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.model.Rent;

public class RentFromDomainToDataMapper {

    @Inject
    private UserFromDomainToDataMapper userToDataMapper;

    @Inject
    private EquipmentFromDomainToDataMapper equipmentToDataMapper;

    public RentEnt convertToDataModel(Rent rent) {
        return new RentEnt(rent.getBeginTime(),
                rent.getEndTime(),
                equipmentToDataMapper.convertToDataModel(rent.getEquipment()),
                userToDataMapper.convertClientToDataModel(rent.getClient())
        );
    }
}
