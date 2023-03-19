package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;

import java.time.LocalDateTime;

public class RentFromDTOToDomainMapper {

    public Rent convertToDomainModel(RentInputDTO rentInputDTO, Equipment equipment, Client client)
            throws RestObjectNotValidException {
        LocalDateTime beginTime;
        LocalDateTime endTime;
        try {
            beginTime = LocalDateTime.parse(rentInputDTO.getBeginTime());
            endTime = LocalDateTime.parse(rentInputDTO.getEndTime());
        } catch (Exception e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        }

        return new Rent (beginTime, endTime, equipment, client);
    }

}
