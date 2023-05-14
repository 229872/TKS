package pl.lodz.p.edu.adapter.rest.users.controller;

import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.users.api.UserService;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.AdminInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.AdminOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestIllegalModificationException;

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
            AdminOutputDTO adminOutputDTO = (AdminOutputDTO) userService.registerUser(adminDTO);
            return Response.status(CREATED).entity(adminOutputDTO).build();
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
        return userServiceFacade.getSingleUser(entityId.toString());
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
