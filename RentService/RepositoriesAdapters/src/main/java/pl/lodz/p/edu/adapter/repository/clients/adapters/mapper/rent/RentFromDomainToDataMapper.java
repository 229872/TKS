package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import pl.lodz.p.edu.core.domain.model.Rent;

@ApplicationScoped
public class RentFromDomainToDataMapper {

    @Inject
    private UserFromDomainToDataMapper userToDataMapper;

    @Inject
    private EquipmentFromDomainToDataMapper equipmentToDataMapper;

    public RentEnt convertToDataModel(Rent rent) {
        RentEnt rentEntR = new RentEnt(
                equipmentToDataMapper.convertToDataModel(rent.getEquipment()),
                userToDataMapper.convertClientToDataModelCreate(rent.getClient()),
                rent.getBeginTime(),
                rent.getEndTime()
        );
        if (rent.getEntityId() != null) {
        rentEntR.setEntityId(rent.getEntityId());
        }
        return rentEntR;
    }

    public RentEnt connectEntitiesToDataModel(Rent rent, ClientEnt clientEnt, EquipmentEnt equipmentEnt) {
        return new RentEnt(equipmentEnt, clientEnt, rent.getBeginTime(), rent.getEndTime());
    }
}
