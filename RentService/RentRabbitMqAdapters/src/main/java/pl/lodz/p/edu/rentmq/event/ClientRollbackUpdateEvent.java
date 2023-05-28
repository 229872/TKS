package pl.lodz.p.edu.rentmq.event;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.rentmq.comm.Backup;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRollbackUpdateEvent extends ClientRollbackBaseEvent{

    @NotNull
    private Backup backup;
}
