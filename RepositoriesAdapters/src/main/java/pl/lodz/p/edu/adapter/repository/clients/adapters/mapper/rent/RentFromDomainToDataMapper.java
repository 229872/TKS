package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent;

import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.model.Rent;

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
