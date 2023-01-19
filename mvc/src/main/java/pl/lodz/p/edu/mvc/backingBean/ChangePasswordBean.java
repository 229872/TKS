package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.data.model.users.Admin;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.data.model.users.User;
import pl.lodz.p.edu.mvc.controller.ChangePasswordController;
import pl.lodz.p.edu.mvc.controller.UserController;

@RequestScoped
@Named
public class ChangePasswordBean {

    @Inject
    private JwtSessionBean jwtSessionBean;

    @Inject
    private ChangePasswordController controller;

    public CredentialsNewPasswordDTO getCredentials() {
        return credentials;
    }

    private CredentialsNewPasswordDTO credentials;

    public boolean isGood() {
        return good;
    }

    private boolean good = true;


    @PostConstruct
    public void init() {
        credentials = new CredentialsNewPasswordDTO();
        credentials.setLogin(jwtSessionBean.getUsername());
    }

    public String change() {
        if(controller.change(credentials)) {
            jwtSessionBean.invalidateSession();
            return "afterChange";
        } else {
            good = false;
            return "";
        }
    }
}
