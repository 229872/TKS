package pl.lodz.p.edu.adapter.rest.adapter.mapper.security.credential;

import pl.lodz.p.edu.adapter.rest.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.core.domain.model.security.CredentialsNewPassword;

public class CredentialsFromNewPasswordDTOToDomainMapper {
    public CredentialsNewPassword convertToDomainModel(CredentialsNewPasswordDTO credentialsDTO) {
        return new CredentialsNewPassword(
                credentialsDTO.getLogin(),
                credentialsDTO.getPassword(),
                credentialsDTO.getNewPassword()
        );
    }
}
