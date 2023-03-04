package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.core.domain.model.Rent;

public class RentFromDomainToDTOMapper {
    public RentDTO convertToDTO(Rent rent) {
        return new RentDTO(
                rent.getClient().getEntityId().toString(),
                rent.getEquipment().getEntityId().toString(),
                rent.getBeginTime().toString(),
                rent.getEndTime().toString()
        );
    }
}
