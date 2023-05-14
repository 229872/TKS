package pl.lodz.p.edu.adapter.rest.users.dto.output;

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
