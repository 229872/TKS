package pl.lodz.p.edu.adapter.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CredentialsNewPasswordDTO {
    @NotBlank(message = "{login.empty}")
    private String login;
    @NotBlank(message = "{password.empty}")
    @Size(min = 8, message = "{password.size}")
    private String password;

    @NotBlank(message = "{password.empty}")
    @Size(min = 8, message = "{password.size}")
    private String newPassword;
}
