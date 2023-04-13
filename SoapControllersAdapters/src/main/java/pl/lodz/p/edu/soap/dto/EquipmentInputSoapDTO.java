package pl.lodz.p.edu.soap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentInputSoapDTO {
    @NotBlank(message = "{equipment.name.empty}")
    private String name;
    @Positive(message = "{equipment.bail.positive}")
    private double bail;
    @Positive(message = "{equipment.firstDayCost.positive}")
    private double firstDayCost;
    @Positive(message = "{equipment.nextDaysCost.positive}")
    private double nextDaysCost;
    private String description;
}
