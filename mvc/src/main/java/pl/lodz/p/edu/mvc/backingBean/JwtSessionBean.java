package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class JwtSessionBean extends AbstractBean {

    private String jwtToken;

    @Inject
    private HttpServletRequest request;

    public JwtSessionBean() {
    }

    @PostConstruct
    public void init() {
        jwtToken = "";
    }

    public void logIn(String jwtToken) {
        this.jwtToken = jwtToken;
        try {
            request.logout();
        } catch (ServletException e) {
            throw new RuntimeException();
        }

    }

    public void invalidateSession() {
        getContext().invalidateSession();
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
