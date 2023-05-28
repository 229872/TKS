package pl.lodz.p.edu.rentmq.event;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRollbackEvent extends ClientRollbackBaseEvent{

    @NotBlank
    private String login;
}
