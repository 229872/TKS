package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.dto.output.ClientOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/clients")
public class ClientController {


    @Inject
    private UserService userService;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response getAll() {
        List<ClientOutputDTO> clients = userService.getAll();
        return Response.ok(clients).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        try {
            return Response.ok(userService.get(entityId)).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
