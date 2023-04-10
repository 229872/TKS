package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent;

import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.core.domain.model.Rent;

public class RentFromDomainToDataMapper {

    @Inject
    private UserFromDomainToDataMapper userToDataMapper;

    @Inject
    private EquipmentFromDomainToDataMapper equipmentToDataMapper;

    public RentEnt convertToDataModel(Rent rent) {
        return new RentEnt(
                equipmentToDataMapper.convertToDataModel(rent.getEquipment()),
                userToDataMapper.convertClientToDataModelALL(rent.getClient()),
                rent.getBeginTime(),
                rent.getEndTime()
        );
    }

    public RentEnt connectEntitiesToDataModel(Rent rent, ClientEnt clientEnt, EquipmentEnt equipmentEnt) {
        return new RentEnt(equipmentEnt, clientEnt, rent.getBeginTime(), rent.getEndTime());
    }
}
