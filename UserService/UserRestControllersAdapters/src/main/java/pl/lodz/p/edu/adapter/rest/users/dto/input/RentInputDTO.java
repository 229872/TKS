package pl.lodz.p.edu.adapter.rest.users.dto.input;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentInputDTO {

    @NotBlank(message = "{rent.equipmentId.empty}")
    private String equipmentUUID;

    @NotBlank(message = "{rent.clientId.empty}")
    private String clientUUID;

    @NotBlank(message = "{rent.beginTime.empty}")
//    @JsonbDateFormat(value = "yyyy-MM-dd")
//    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")
    //fixme too much work with these kinds of regex
    private String beginTime;

    private String endTime;

    public UUID getClientUUIDFromString() {
        return UUID.fromString(this.clientUUID);
    }

    public UUID getEquipmentUUIDFromString() {
        return UUID.fromString(this.equipmentUUID);
    }
}
