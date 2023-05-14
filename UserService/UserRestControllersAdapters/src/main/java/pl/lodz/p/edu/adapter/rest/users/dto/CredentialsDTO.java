package pl.lodz.p.edu.adapter.rest.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CredentialsDTO {

    @NotBlank(message = "{login.empty}")
    private String login;

    @NotBlank(message = "{password.empty}")
    @Size(min = 8, message = "{password.size}")
    private String password;
}
