package pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.model.Rent;

@ApplicationScoped
public class RentFromDataToDomainMapper {

    @Inject
    private EquipmentFromDataToDomainMapper equipmentToDomainMapper;

    @Inject
    private UserFromDataToDomainMapper userToDomainMapper;

    public Rent convertToDomainModel(RentEnt rentEnt) {
        return new Rent (
                rentEnt.getEntityId(),
                rentEnt.getBeginTime(),
                rentEnt.getEndTime(),
                equipmentToDomainMapper.convertToDomainModel(
                        rentEnt.getEquipmentEnt()
                ),
                userToDomainMapper.convertClientToDomainModel(
                        rentEnt.getClientEnt()
                )
        );
    }
}
