package pl.lodz.p.edu.rest.authentication;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.data.model.DTO.CredentialsDTO;
import pl.lodz.p.edu.data.model.DTO.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.rest.exception.AuthenticationFailureException;

@Path("/login")
public class AuthenticationController {

    @Inject
    AuthenticationManager authManager;

    @POST
    @Path("/")
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

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(@Valid CredentialsNewPasswordDTO credentials) {
        try {
            return Response.status(200).entity(authManager.changePassword(credentials)).build();
        } catch (AuthenticationFailureException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
    }
}
