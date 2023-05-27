package pl.lodz.p.edu;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClientUpdateEvent {
    @NotNull
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
