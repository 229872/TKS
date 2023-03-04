package pl.lodz.p.edu.adapter.rest.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.AuthenticationService;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.users.UserDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@RequestScoped
public class UserControllerMethods {

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;

    public Response searchUser(String type, String login) {
        if(login != null) {
            List<UserDTO> searchResult = userService.searchOfType(type, login);
            return Response.status(OK).entity(searchResult).build();
        }
        List<UserDTO> users = userService.getAllUsersOfType(type);
        return Response.status(OK).entity(users).build();
    }

    public Response getSingleUser(String type, UUID entityId) {
        try {
            UserDTO user = userService.getUserByUuidOfType(type, entityId);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", user.getLogin());
            String ifMatchTag = authenticationService.signLogin(jsonObject.toString());

            return Response.status(OK).entity(user).tag(ifMatchTag).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        } catch (JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    public Response getSingleUser(String type, String login) {
        try {
            UserDTO user = userService.getUserByLoginOfType(type, login);
            return Response.status(OK).entity(user).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response activateUser(String type, UUID entityId) {
        try {
            userService.activateUser(type, entityId);
            return Response.status(NO_CONTENT).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response deactivateUser(String type, UUID entityId) {
        try {
            userService.deactivateUser(type, entityId);
            return Response.status(NO_CONTENT).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }


    //tmp fixme
    public void verifySingedLogin(String ifMatch, JsonObject jsonDTO) throws ParseException,
            RestAuthenticationFailureException, JOSEException {
        authenticationService.verifySingedLogin(ifMatch, String.valueOf(jsonDTO));
    }
}
