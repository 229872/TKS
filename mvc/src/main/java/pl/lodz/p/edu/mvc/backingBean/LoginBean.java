package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import pl.lodz.p.edu.data.model.DTO.CredentialsDTO;
import pl.lodz.p.edu.mvc.controller.LoginController;

import javax.ws.rs.core.SecurityContext;

@Named
@SessionScoped
public class LoginBean extends AbstractBean {

//    @Inject
//    private JwtSessionBean jwtSessionBean;


    @Inject
    private HttpServletRequest request;

    @Inject
    private LoginController loginController;

    private CredentialsDTO credentialsDTO = new CredentialsDTO();

    public boolean isIncorrectPassword() {
        return incorrectPassword;
    }

    private boolean incorrectPassword = false;

    public LoginBean() {
    }

    @PostConstruct
    public void init() {
        credentialsDTO = new CredentialsDTO();
    }

    public String login() {
        String jwtToken;

        try {
            jwtToken = loginController.logIn(credentialsDTO);
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // error with localisation idk resource bundle
            throw new RuntimeException("niewiadomo login()");
        }

        incorrectPassword = jwtToken == null;
        return "";
    }

    public String getRole() {
        if (request.isUserInRole("CLIENT")) return "CLIENT";
        if (request.isUserInRole("EMPLOYEE")) return "EMPLOYEE";
        if (request.isUserInRole("ADMIN")) return "ADMIN";
        return "GUEST";
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }
}
