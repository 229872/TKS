package pl.lodz.p.edu;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
public class ClientCreatedEvent {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
