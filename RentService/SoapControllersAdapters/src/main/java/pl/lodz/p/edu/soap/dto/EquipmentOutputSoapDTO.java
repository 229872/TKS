package pl.lodz.p.edu.soap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentOutputSoapDTO {
    private UUID entityId;
    private String name;
    private double bail;
    private double firstDayCost;
    private double nextDaysCost;
    private String description;
}
