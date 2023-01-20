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


@Named
@SessionScoped
public class LoginBean extends AbstractBean {

    @Inject
    private JwtSessionBean jwtSessionBean;

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
            throw new RuntimeException("Error during login");
        }

        incorrectPassword = jwtToken == null;
        if(incorrectPassword) {
            return "";
        }
        jwtSessionBean.logIn(jwtToken);
        return "afterLogin";
    }

    public boolean isInRole(String role) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        return context.isUserInRole(role);
    }

    public String getRole() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        if (context.isUserInRole("CLIENT")) return "CLIENT";
        if (context.isUserInRole("EMPLOYEE")) return "EMPLOYEE";
        if (context.isUserInRole("ADMIN")) return "ADMIN";
        return "GUEST";
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }
}
