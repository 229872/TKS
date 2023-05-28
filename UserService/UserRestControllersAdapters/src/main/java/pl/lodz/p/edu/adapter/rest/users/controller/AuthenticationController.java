package pl.lodz.p.edu.adapter.rest.users.controller;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.users.dto.CredentialsDTO;
import pl.lodz.p.edu.adapter.rest.users.dto.CredentialsNewPasswordDTO;
import pl.lodz.p.edu.adapter.rest.users.exception.*;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.AuthenticationFailureException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.userports.incoming.AuthenticationServicePort;

@Path("/auth")
public class AuthenticationController {

    @Inject
    private AuthenticationServicePort authManager;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(@Valid CredentialsDTO credentialsDTO) {
        try {
            return Response.status(200).entity(authManager.
                    login(credentialsDTO.getLogin(), credentialsDTO.getPassword())).build();
        } catch (ObjectNotFoundServiceException | AuthenticationFailureException e) {
            return Response.status(401).entity(e.getMessage()).build();
        }
    }
}
