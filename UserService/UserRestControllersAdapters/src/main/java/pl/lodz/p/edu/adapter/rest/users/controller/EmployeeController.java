package pl.lodz.p.edu.adapter.rest.users.controller;

import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.users.api.UserService;
import pl.lodz.p.edu.adapter.rest.users.dto.input.users.EmployeeInputDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.output.users.EmployeeOutputDTO;
import pl.lodz.p.edu.adapter.rest.users.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.users.exception.RestIllegalModificationException;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/employees")
public class EmployeeController {

    @Inject
    private UserService userService;

    @Inject
    private UserServiceFacade userServiceFacade;

    protected EmployeeController() {
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN"})
    public Response addEmployee(@Valid EmployeeInputDTO employeeDTO) {
        try {
            EmployeeOutputDTO registeredEmployee = (EmployeeOutputDTO) userService.registerUser(employeeDTO);
            return Response.status(CREATED).entity(registeredEmployee).build();
        } catch (RestConflictException | TransactionalException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getAll() {
        List<EmployeeOutputDTO> employees = userServiceFacade.getEmployees();
        return Response.ok(employees).build();
    }

    @GET
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByUuid(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.getSingleUser(entityId.toString());
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userServiceFacade.getSingleUser(login);
    }

    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response updateEmployee(@PathParam("entityId") UUID entityId, @HeaderParam("IF-MATCH") String ifMatch,
                                   @Valid EmployeeInputDTO employeeDTO) {
//        JsonObject jsonDTO = new JsonObject();
//        jsonDTO.addProperty("login", employeeDTO.getLogin());
//        try {
//            userServiceFacade.verifySingedLogin(ifMatch, jsonDTO);
//        } catch (ParseException | RestAuthenticationFailureException | JOSEException e) {
//            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
//        }
        try {
            userService.updateEmployee(entityId, employeeDTO);
            return Response.status(OK).entity(employeeDTO).build();
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
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.activateUser(entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
//    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userServiceFacade.deactivateUser(entityId);
    }
}
