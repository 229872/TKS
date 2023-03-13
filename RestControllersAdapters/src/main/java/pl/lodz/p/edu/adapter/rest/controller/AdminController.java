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
import pl.lodz.p.edu.adapter.rest.dto.users.AdminDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.AuthenticationFailureException;

import java.text.ParseException;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/admins")
public class AdminController {

    @Inject
    private UserService userService;

    @Inject
    private UserControllerMethods userControllerMethods;


    protected AdminController() {}

    // create

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response addAdmin(@Valid AdminDTO adminDTO) {
        try {
            userService.registerAdmin(adminDTO);
            return Response.status(CREATED).entity(adminDTO).build();
        } catch(RestConflictException | TransactionalException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.searchUser("Admin", login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser("Admin", entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userControllerMethods.getSingleUser("Admin", login);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response updateAdmin(@PathParam("entityId") UUID entityId, @HeaderParam("IF-MATCH") String ifMatch,
                                @Valid AdminDTO adminDTO) {
        JsonObject jsonDTO = new JsonObject();
        jsonDTO.addProperty("login", adminDTO.getLogin());
        try {
            userControllerMethods.verifySingedLogin(ifMatch, jsonDTO);
        } catch (ParseException | RestAuthenticationFailureException | JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }

        try {
            userService.updateAdmin(entityId, adminDTO);
            return Response.status(OK).entity(adminDTO).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
//    @RolesAllowed({"ADMIN"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser("Admin", entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
//    @RolesAllowed({"ADMIN"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser("Admin", entityId);
    }
}
