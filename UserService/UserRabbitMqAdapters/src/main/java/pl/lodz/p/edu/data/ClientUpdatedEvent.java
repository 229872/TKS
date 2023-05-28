package pl.lodz.p.edu.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientUpdatedEvent {

    private String login;

    private String firstName;

    private String lastName;

    private String firstNameBackup;

    private String lastNameBackup;

}
