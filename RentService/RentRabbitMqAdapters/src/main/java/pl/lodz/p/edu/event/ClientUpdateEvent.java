package pl.lodz.p.edu.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.comm.Backup;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateEvent {
    @NotNull
    private UUID id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Backup backup;
}
