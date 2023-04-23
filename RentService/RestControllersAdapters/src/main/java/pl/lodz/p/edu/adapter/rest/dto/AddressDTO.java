package pl.lodz.p.edu.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDTO {
    @NotBlank(message = "{city.empty}")
    private String city;
    @NotBlank(message = "{street.empty}")
    private String street;
    @NotBlank(message = "{streetNumber.empty}")
    private String streetNr;
}
