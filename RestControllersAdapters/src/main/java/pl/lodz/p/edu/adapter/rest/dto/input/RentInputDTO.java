package pl.lodz.p.edu.adapter.rest.dto.input;

import jakarta.json.bind.annotation.JsonbDateFormat;
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
    private String beginTime;

    private String endTime;

    public UUID getClientUUIDFromString() {
        return UUID.fromString(this.clientUUID);
    }

    public UUID getEquipmentUUIDFromString() {
        return UUID.fromString(this.equipmentUUID);
    }
}
