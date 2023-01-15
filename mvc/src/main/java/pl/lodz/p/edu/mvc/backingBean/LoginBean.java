package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
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

        if (jwtToken != null) {
            jwtSessionBean.logIn(jwtToken);
            return "";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            throw new RuntimeException("niewiadomo (jwtToken != null) else -> ");
        }
//        return "";
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }
}
