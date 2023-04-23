package pl.lodz.p.edu.adapter.rest.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.AuthenticationService;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.UserTypeDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.UserInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.EmployeeOutputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.UserOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static jakarta.ws.rs.core.Response.Status.*;

@ApplicationScoped
public class UserServiceFacade {

    @Inject
    private UserService userService;

    @Inject
    private AuthenticationService authenticationService;


    //FIXME what does id do ?
    public Response getSingleUser(UUID entityId) {
        try {
            UserOutputDTO user = userService.get(entityId);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("login", user.getLogin());
            String ifMatchTag = authenticationService.signLogin(jsonObject.toString());

            return Response.status(OK).entity(user).tag(ifMatchTag).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (JOSEException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    public Response getSingleUser(String login) {
        try {
            UserOutputDTO user = userService.getByLogin(login);
            return Response.status(OK).entity(user).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response activateUser(UUID entityId) {
        try {
            userService.activateUser(entityId);
            return Response.status(NO_CONTENT).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response deactivateUser(UUID entityId) {
        try {
            userService.deactivateUser(entityId);
            return Response.status(NO_CONTENT).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }


    //tmp fixme
    public void verifySingedLogin(String ifMatch, JsonObject jsonDTO) throws ParseException,
            RestAuthenticationFailureException, JOSEException {
        authenticationService.verifySingedLogin(ifMatch, String.valueOf(jsonDTO));
    }


    public List<UserOutputDTO> getAll() {
        return userService.getAll();
    }

    public List<ClientOutputDTO> getClients() {
        return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.CLIENT))
                .map(user -> (ClientOutputDTO) user)
                .collect(Collectors.toList());
    }

    public List<AdminOutputDTO> getAdmins() {
        return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.ADMIN))
                .map(user -> (AdminOutputDTO) user)
                .collect(Collectors.toList());
    }

    public List<EmployeeOutputDTO> getEmployees() {
        return userService.getAll().stream()
                .filter(user -> user.getUserType().equals(UserTypeDTO.EMPLOYEE))
                .map(user -> (EmployeeOutputDTO) user)
                .collect(Collectors.toList());
    }
}
