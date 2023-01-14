package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.CredentialsDTO;
import pl.lodz.p.edu.mvc.controller.LoginController;

@Named
@ViewScoped
public class LoginBean extends AbstractBean {

    @Inject
    JwtSessionBean jwtSessionBean;

    @Inject
    LoginController loginController;

    private CredentialsDTO credentialsDTO;

    @PostConstruct
    public void init() {
        credentialsDTO = new CredentialsDTO();
    }

    public String login() {
        String jwtToken = null;
        try {
            jwtToken = loginController.logIn(credentialsDTO);
        } catch (Exception e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // error with localisation idk resource bundle
        }

        if (jwtToken != null) {
            jwtSessionBean.logIn(jwtToken);
            return "java server ugly faces";
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            // error with localisation idk resource bundle

        }
        return "";
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }
}
