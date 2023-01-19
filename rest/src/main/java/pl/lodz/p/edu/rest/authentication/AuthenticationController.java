package pl.lodz.p.edu.rest.authentication;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.data.model.DTO.CredentialsDTO;
import pl.lodz.p.edu.data.model.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;

@Path("/")
public class AuthenticationController {

    @Inject
    private AuthenticationManager authManager;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid CredentialsDTO credentialsDTO) {
        try {
            return Response.status(200).entity(authManager.
                    login(credentialsDTO.getLogin(), credentialsDTO.getPassword())).build();
        } catch (AuthenticationFailureException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/changePassword")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"CLIENT", "EMPLOYEE", "ADMIN"})
    public Response changePassword(@Valid CredentialsNewPasswordDTO credentials) {
        try {
            return Response.status(200).entity(authManager.changePassword(credentials)).build();
        } catch (AuthenticationFailureException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
    }
}
