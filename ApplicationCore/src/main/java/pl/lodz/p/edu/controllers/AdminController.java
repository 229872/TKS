package pl.lodz.p.edu.controllers;

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
import pl.lodz.p.edu.exception.AuthenticationFailureException;
import pl.lodz.p.edu.exception.ConflictException;
import pl.lodz.p.edu.exception.IllegalModificationException;
import pl.lodz.p.edu.DTO.users.AdminDTO;
import pl.lodz.p.edu.model.users.Admin;
import pl.lodz.p.edu.service.api.UserService;
import pl.lodz.p.edu.util.JwtUtilities;
import pl.lodz.p.edu.util.DataFaker;

import java.text.ParseException;
import java.util.UUID;
import java.util.logging.Logger;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.CONFLICT;

@Path("/admins")
public class AdminController {

    Logger logger = Logger.getLogger(AdminController.class.getName());
    @Inject
    private UserService userService;

    @Inject
    private UserControllerMethods userControllerMethods;

    @Inject
    private JwtUtilities jwtUtilities;

    protected AdminController() {}

    // create

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response addAdmin(@Valid AdminDTO adminDTO) {
        try {
            Admin admin = new Admin(adminDTO);
            userService.registerAdmin(admin);
            return Response.status(CREATED).entity(admin).build();
        } catch(ConflictException e) {
            return Response.status(CONFLICT).build();
        } catch(TransactionalException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response searchAdmin(@QueryParam("login") String login) {
        return userControllerMethods.searchUser("Admin", login);
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response getUserByUuid(@PathParam("uuid") UUID entityId) {
        return userControllerMethods.getSingleUser("Admin", entityId);
    }

    @GET
    @Path("/login/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response getUserByLogin(@PathParam("login") String login) {
        return userControllerMethods.getSingleUser("Admin", login);
    }

    // update
    @PUT
    @Path("/{entityId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Response updateAdmin(@PathParam("entityId") UUID entityId, @HeaderParam("IF-MATCH") String ifMatch,
                                @Valid AdminDTO adminDTO) {
        JsonObject jsonDTO = new JsonObject();
        jsonDTO.addProperty("login", adminDTO.getLogin());
        try {
            jwtUtilities.verifySingedLogin(ifMatch, String.valueOf(jsonDTO));
        } catch (ParseException | AuthenticationFailureException | JOSEException e) {
            return Response.status(BAD_REQUEST).build();
        }

        try {
            userService.updateAdmin(entityId, adminDTO);
            return Response.status(OK).entity(adminDTO).build();
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
    @RolesAllowed({"ADMIN"})
    public Response activateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.activateUser("Admin", entityId);
    }

    @PUT
    @Path("/{entityId}/deactivate")
    @RolesAllowed({"ADMIN"})
    public Response deactivateUser(@PathParam("entityId") UUID entityId) {
        return userControllerMethods.deactivateUser("Admin", entityId);
    }

    // other

    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN"})
    public Admin addFakeUserAdmin() {
        Admin c = DataFaker.getAdmin();
        try {
            userService.registerAdmin(c);
        } catch(Exception e) {
            return null;
        }
        return c;
    }
}
