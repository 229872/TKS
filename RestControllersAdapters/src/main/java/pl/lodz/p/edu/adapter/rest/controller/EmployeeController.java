package pl.lodz.p.edu.adapter.rest.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.users.EmployeeDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;

import java.text.ParseException;
import java.util.UUID;
import java.util.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/employees")
public class EmployeeController {

    @Inject
    private UserService userService;

    @Inject
    private UserControllerMethods userControllerMethods;

    protected EmployeeController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response addEmployee(@Valid EmployeeDTO employeeDTO) {
        try {
            EmployeeDTO registeredEmployee = (EmployeeDTO) userService.registerUser(employeeDTO);
            return Response.status(CREATED).entity(registeredEmployee).build();
        } catch(RestConflictException | TransactionalException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.getSingleUser(login);
    }

    @GET
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByUuid(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.getSingleUser(entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userControllerMethods.getSingleUser(login);
    }

    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response updateEmployee(@PathParam("entityId") UUID entityId, @HeaderParam("IF-MATCH") String ifMatch,
                                   @Valid EmployeeDTO employeeDTO) {
        JsonObject jsonDTO = new JsonObject();
        jsonDTO.addProperty("login", employeeDTO.getLogin());
        try {
            userControllerMethods.verifySingedLogin(ifMatch, jsonDTO);
        } catch (ParseException | RestAuthenticationFailureException | JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }
        try {
            userService.updateEmployee(entityId, employeeDTO);
            return Response.status(OK).entity(employeeDTO).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser(entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser(entityId);
    }
}
