package pl.lodz.p.edu.adapter.rest.controller;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/admins")
public class AdminController {

    @Inject
    private UserService userService;

    @Inject
    private UserServiceFacade userServiceFacade;


    protected AdminController() {}

    // create

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response addAdmin(@Valid AdminInputDTO adminDTO) {
        try {
            userService.registerUser(adminDTO);
            return Response.status(CREATED).entity(adminDTO).build();
        } catch(RestConflictException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        }
    }

    // read
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response getAll() {
        List<AdminOutputDTO> admins = userServiceFacade.getAdmins();
        return Response.ok(admins).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userServiceFacade.getSingleUser(entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userServiceFacade.getSingleUser(login);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response updateAdmin(@PathParam("entityId") UUID entityId, @Valid AdminInputDTO adminDTO) {
        try {
            userService.updateAdmin(entityId, adminDTO);
            return Response.status(OK).entity(adminDTO).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
//    @RolesAllowed({"ADMIN"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.activateUser(entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
//    @RolesAllowed({"ADMIN"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.deactivateUser(entityId);
    }
}
