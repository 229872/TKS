package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalDateException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Client;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@ApplicationScoped
public class RentFromDTOToDomainMapper {

    public Rent convertToDomainModel(RentInputDTO rentInputDTO, Equipment equipment, Client client)
            throws RestObjectNotValidException, RestIllegalDateException {
        LocalDateTime beginTime;
        LocalDateTime endTime;
        try {
            beginTime = LocalDateTime.parse(rentInputDTO.getBeginTime());
            endTime = rentInputDTO.getEndTime() == null ? null : LocalDateTime.parse(rentInputDTO.getEndTime());

        } catch (DateTimeParseException e) {
            throw new RestIllegalDateException(e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new RestObjectNotValidException(e.getMessage(), e.getCause());
        }

        return new Rent (beginTime, endTime, equipment, client);
    }

}
