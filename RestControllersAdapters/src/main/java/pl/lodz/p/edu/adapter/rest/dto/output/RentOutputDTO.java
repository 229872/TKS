package pl.lodz.p.edu.adapter.rest.dto.output;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RentOutputDTO {
    private UUID rentId;
    private String equipmentUUID;
    private String clientUUID;
    private String beginTime;
    private String endTime;
}
