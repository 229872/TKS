package pl.lodz.p.edu.adapter.rest.dto.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentOutputDTO {
    private UUID equipmentId;
    private String name;
    private double bail;
    private double firstDayCost;
    private double nextDaysCost;
    private String description;
}
