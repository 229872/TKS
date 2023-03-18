package pl.lodz.p.edu.adapter.rest.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.AuthenticationService;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.users.*;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.*;


public class UserServiceFacade {

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;


    //FIXME what does id do ?
    public Response getSingleUser(UUID entityId) {
        try {
            UserDTO user = userService.get(entityId);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", user.getLogin());
            String ifMatchTag = authenticationService.signLogin(jsonObject.toString());

            return Response.status(OK).entity(user).tag(ifMatchTag).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        } catch (JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    public Response getSingleUser(String login) {
        try {
            UserDTO user = userService.getByLogin(login);
            return Response.status(OK).entity(user).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response activateUser(UUID entityId) {
        try {
            userService.activateUser(entityId);
            return Response.status(NO_CONTENT).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    public Response deactivateUser(UUID entityId) {
        try {
            userService.deactivateUser(entityId);
            return Response.status(NO_CONTENT).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }


    //tmp fixme
    public void verifySingedLogin(String ifMatch, JsonObject jsonDTO) throws ParseException,
            RestAuthenticationFailureException, JOSEException {
        authenticationService.verifySingedLogin(ifMatch, String.valueOf(jsonDTO));
    }


    public List<UserDTO> getAll() {
       return userService.getAll();
    }

    public List<ClientDTO> getClients() {
        return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.CLIENT))
                .map(user -> (ClientDTO) user)
                .collect(Collectors.toList());
    }

    public List<AdminDTO> getAdmins() {
      return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.ADMIN))
                .map(user -> (AdminDTO) user)
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> getEmployees() {
        return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.EMPLOYEE))
                .map(user -> (EmployeeDTO) user)
                .collect(Collectors.toList());
    }
}
