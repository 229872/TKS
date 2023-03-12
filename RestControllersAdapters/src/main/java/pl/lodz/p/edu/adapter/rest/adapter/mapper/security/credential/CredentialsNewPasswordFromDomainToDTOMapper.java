package pl.lodz.p.edu.adapter.rest.adapter.mapper.security.credential;

import pl.lodz.p.edu.adapter.rest.dto.CredentialsDTO;
import pl.lodz.p.edu.adapter.rest.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;

public class CredentialsNewPasswordFromDomainToDTOMapper {
    public CredentialsNewPasswordDTO convertToDTO(CredentialsNewPassword credentialsNewPassword) {
        return new CredentialsNewPasswordDTO(
                credentialsNewPassword.getLogin(),
                credentialsNewPassword.getPassword(),
                credentialsNewPassword.getNewPassword()
        );
    }
}
