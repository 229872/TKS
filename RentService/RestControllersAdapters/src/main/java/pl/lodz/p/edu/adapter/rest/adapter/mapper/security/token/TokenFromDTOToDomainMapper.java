package pl.lodz.p.edu.adapter.rest.adapter.mapper.security.token;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.rest.dto.TokenDTO;
import pl.lodz.p.edu.core.domain.model.security.Token;

@ApplicationScoped
public class TokenFromDTOToDomainMapper {
    public Token convertToDomainModel(TokenDTO tokenDTO) {
        return new Token(
                tokenDTO.getJwt()
        );
    }
}
