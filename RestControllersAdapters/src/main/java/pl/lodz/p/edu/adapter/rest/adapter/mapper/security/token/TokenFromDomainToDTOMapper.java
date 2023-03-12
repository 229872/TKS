package pl.lodz.p.edu.adapter.rest.adapter.mapper.security.token;

import pl.lodz.p.edu.adapter.rest.dto.TokenDTO;
import pl.lodz.p.edu.core.domain.model.security.Token;

public class TokenFromDomainToDTOMapper {
    public TokenDTO convertToDTO(Token token) {
        return new TokenDTO(token.getJwt());
    }
}
