package pl.lodz.p.edu.adapter.rest.users.controller;

import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.users.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.users.api.UserService;
import java.util.List;
import java.util.UUID;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.ClientInputDTO;
import pl.lodz.p.edu.adapter.rest.users.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestIllegalModificationException;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/clients")
public class ClientController {

    @Inject
    private UserService userService;

    @Inject
    private UserServiceFacade userServiceFacade;


    protected ClientController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response addClient(@Valid ClientInputDTO clientDTO) {
        try {
            ClientOutputDTO registeredClient = (ClientOutputDTO) userService.registerUser(clientDTO);
            return Response.status(CREATED).entity(registeredClient).build();
        } catch(RestConflictException | TransactionalException e ) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response getAll() {
        List<ClientOutputDTO> clients = userServiceFacade.getClients();
        return Response.ok(clients).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userServiceFacade.getSingleUser(entityId.toString());
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userServiceFacade.getSingleUser(login);
    }

    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @Allowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response updateClient(@PathParam("entityId") UUID entityId, @Valid ClientInputDTO clientDTO) {
        try {
            userService.updateClient(entityId, clientDTO);
            return Response.status(OK).entity(clientDTO).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch(TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.activateUser(entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.deactivateUser(entityId);
    }
}
