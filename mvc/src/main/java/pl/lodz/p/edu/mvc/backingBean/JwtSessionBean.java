package pl.lodz.p.edu.mvc.backingBean;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@SessionScoped
@Named
public class JwtSessionBean extends AbstractBean {

    private String jwtToken = "";

    @Inject
    private HttpServletRequest request;

    public JwtSessionBean() {
    }

    public void logIn(String jwtToken) {
        this.jwtToken = jwtToken;
        try {
            request.logout(); //Todo there is other way to achieve this?
        } catch (ServletException e) {
            throw new RuntimeException();
        }

    }

    public String getRole() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (context.isUserInRole("CLIENT")) return "CLIENT";
        if (context.isUserInRole("EMPLOYEE")) return "EMPLOYEE";
        if (context.isUserInRole("ADMIN")) return "ADMIN";
        return "GUEST";
    }

    public void invalidateSession() {
        getContext().invalidateSession();
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
