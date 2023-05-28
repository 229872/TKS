package pl.lodz.p.edu;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientCreatedEvent {

    @NotBlank
    private String login;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
