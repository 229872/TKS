package pl.lodz.p.edu.rest.controllers;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.managers.api.UserManager;
import pl.lodz.p.edu.rest.util.JwtUtilities;
import pl.lodz.p.edu.rest.managers.impl.UserManagerImpl;
import pl.lodz.p.edu.data.model.users.User;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@RequestScoped
public class UserControllerMethods {

    @Inject
    private UserManager userManager;

    @Inject
    private JwtUtilities jwtUtilities;

    public Response searchUser(String type, String login) {
        if(login != null) {
            List<User> searchResult = userManager.searchOfType(type, login);
            return Response.status(OK).entity(searchResult).build();
        }
        List<User> users = userManager.getAllUsersOfType(type);
        return Response.status(OK).entity(users).build();
    }

    public Response getSingleUser(String type, UUID entityId) {
        try {
            User user = userManager.getUserByUuidOfType(type, entityId);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", user.getLogin());
            String ifMatchTag = jwtUtilities.signLogin(jsonObject.toString());

            return Response.status(OK).entity(user).tag(ifMatchTag).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        } catch (JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    public Response getSingleUser(String type, String login) {
        try {
            User user = userManager.getUserByLoginOfType(type, login);
            return Response.status(OK).entity(user).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response activateUser(String type, UUID entityId) {
        try {
            userManager.activateUser(type, entityId);
            return Response.status(NO_CONTENT).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response deactivateUser(String type, UUID entityId) {
        try {
            userManager.deactivateUser(type, entityId);
            return Response.status(NO_CONTENT).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }
}
