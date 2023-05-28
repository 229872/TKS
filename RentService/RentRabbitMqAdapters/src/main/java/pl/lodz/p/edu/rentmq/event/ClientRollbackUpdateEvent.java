package pl.lodz.p.edu.rentmq.event;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.core.domain.other.Backup;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRollbackUpdateEvent extends ClientRollbackBaseEvent{

    @NotBlank
    private String login;

    @NotNull
    private String name;

    @NotNull
    private String lastName;
}
