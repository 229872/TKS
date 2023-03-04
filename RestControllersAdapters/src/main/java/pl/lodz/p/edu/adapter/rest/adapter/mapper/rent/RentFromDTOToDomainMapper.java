package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.equipment.EquipmentFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.adapter.mapper.user.UserFromDTOToDomainMapper;
import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.ClientDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;

import java.time.LocalDateTime;

public class RentFromDTOToDomainMapper {

    public Rent convertToDomainModel(RentDTO rentDTO, Equipment equipment, Client client)
            throws RestObjectNotValidException {
        LocalDateTime beginTime;
        LocalDateTime endTime;
        try {
            beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
            endTime = LocalDateTime.parse(rentDTO.getEndTime());
        } catch (Exception e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        }

        return new Rent (beginTime, endTime, equipment, client);
    }

}
