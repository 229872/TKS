package pl.lodz.p.edu.adapter.rest.adapter.mapper.rent;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.RentOutputDTO;
import pl.lodz.p.edu.core.domain.model.Rent;

@ApplicationScoped
public class RentFromDomainToDTOMapper {
    public RentInputDTO convertToInputDTO(Rent rent) {
        return new RentInputDTO(
                rent.getClient().getEntityId().toString(),
                rent.getEquipment().getEntityId().toString(),
                rent.getBeginTime().toString(),
                rent.getEndTime() == null ? null : rent.getEndTime().toString()
        );
    }

    public RentOutputDTO convertToOutputDTO(Rent rent) {
        return new RentOutputDTO(
                rent.getEntityId(),
                rent.getClient().getEntityId().toString(),
                rent.getEquipment().getEntityId().toString(),
                rent.getBeginTime().toString(),
                rent.getEndTime() == null ? null : rent.getEndTime().toString()
        );
    }

}
