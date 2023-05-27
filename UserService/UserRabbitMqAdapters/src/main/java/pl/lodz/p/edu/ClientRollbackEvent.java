package pl.lodz.p.edu;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ClientRollbackEvent {
    @NotNull
    private UUID id;
}
