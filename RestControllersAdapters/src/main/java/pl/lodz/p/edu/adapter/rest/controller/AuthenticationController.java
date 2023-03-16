package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.AuthenticationService;
import pl.lodz.p.edu.adapter.rest.dto.CredentialsDTO;
import pl.lodz.p.edu.adapter.rest.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestAuthenticationFailureException;

@Path("/")
public class AuthenticationController {

    @Inject
    private AuthenticationService authManager;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid CredentialsDTO credentialsDTO) {
        try {
            return Response.status(200).entity(authManager.
                    login(credentialsDTO.getLogin(), credentialsDTO.getPassword())).build();
        } catch (RestAuthenticationFailureException e) {
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
            authManager.changePassword(credentials);
            return Response.ok().build();
        } catch (RestAuthenticationFailureException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
    }
}
