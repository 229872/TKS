package pl.lodz.p.edu.core.domain.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientUpdateEvent {

    private String login;

    private String firstName;

    private String lastName;

    private String firstNameBackup;

    private String lastNameBackup;
}
