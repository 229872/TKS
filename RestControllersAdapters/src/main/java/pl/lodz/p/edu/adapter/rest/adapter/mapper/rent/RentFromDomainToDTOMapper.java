package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.core.domain.model.Rent;

public class RentFromDomainToDTOMapper {
    public RentInputDTO convertToDTO(Rent rent) {
        return new RentInputDTO(
                rent.getClient().getEntityId().toString(),
                rent.getEquipment().getEntityId().toString(),
                rent.getBeginTime().toString(),
                rent.getEndTime().toString()
        );
    }
}
