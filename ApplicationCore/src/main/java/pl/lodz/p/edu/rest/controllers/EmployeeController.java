package pl.lodz.p.edu.rest.controllers;

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
import pl.lodz.p.edu.rest.DTO.users.EmployeeDTO;
import pl.lodz.p.edu.rest.managers.api.UserManager;
import pl.lodz.p.edu.rest.util.JwtUtilities;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.model.users.Employee;
import pl.lodz.p.edu.rest.util.DataFaker;

import java.text.ParseException;
import java.util.UUID;
import java.util.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.OK;

@Path("/employees")
public class EmployeeController {

    Logger logger = Logger.getLogger(AdminController.class.getName());

    @Inject
    private UserManager userManager;

    @Inject
    private UserControllerMethods userControllerMethods;

    @Inject
    private JwtUtilities jwtUtilities;

    protected EmployeeController() {}

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response addEmployee(@Valid EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee(employeeDTO);
            userManager.registerEmployee(employee);
            return Response.status(CREATED).entity(employee).build();
        } catch(ConflictException | TransactionalException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.searchUser("Employee", login);
    }

    @GET
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByUuid(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.getSingleUser("Employee", entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userControllerMethods.getSingleUser("Employee", login);
    }

    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response updateEmployee(@PathParam("entityId") UUID entityId, @HeaderParam("IF-MATCH") String ifMatch,
                                   @Valid EmployeeDTO employeeDTO) {
        JsonObject jsonDTO = new JsonObject();
        jsonDTO.addProperty("login", employeeDTO.getLogin());
        try {
            jwtUtilities.verifySingedLogin(ifMatch, String.valueOf(jsonDTO));
        } catch (ParseException | AuthenticationFailureException | JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }
        try {
            userManager.updateEmployee(entityId, employeeDTO);
            return Response.status(OK).entity(employeeDTO).build();
        } catch (IllegalModificationException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(TransactionalException e) { // login modification
            return Response.status(BAD_REQUEST).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{entityId}/activate")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser("Employee", entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser("Employee", entityId);
    }

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "EMPLOYEE"})
    public Employee addFakeUserAdmin() {
        Employee c = DataFaker.getEmployee();
        try {
            userManager.registerEmployee(c);
        } catch (Exception e) {
            return null;
        }
        return c;
    }
}