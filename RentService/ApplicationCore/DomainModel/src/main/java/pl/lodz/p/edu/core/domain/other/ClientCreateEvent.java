package pl.lodz.p.edu.core.domain.other;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ClientCreateEvent {

    private String login;
    private String firstName;
    private String lastName;
}
