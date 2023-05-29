package pl.lodz.p.edu.userports.incoming;

import pl.lodz.p.edu.user.core.domain.usermodel.exception.AuthenticationFailureException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;

public interface AuthenticationServicePort {
    String login(String login, String password) throws ObjectNotFoundServiceException, AuthenticationFailureException;
}
