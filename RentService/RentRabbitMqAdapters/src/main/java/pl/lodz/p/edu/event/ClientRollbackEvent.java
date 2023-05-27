package pl.lodz.p.edu.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRollbackEvent extends ClientRollbackBaseEvent{

    @NotBlank
    private String login;
}
