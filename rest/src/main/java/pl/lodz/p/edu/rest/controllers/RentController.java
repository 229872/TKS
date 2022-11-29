package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.managers.EquipmentManager;
import pl.lodz.p.edu.rest.managers.RentManager;
import pl.lodz.p.edu.rest.managers.UserManager;
import pl.lodz.p.edu.rest.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.model.Rent;
import pl.lodz.p.edu.rest.model.users.Client;
import pl.lodz.p.edu.rest.model.users.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Path("/rents")
public class RentController {

    @Inject
    private RentManager rentManager;

    @Inject
    private UserManager userManager;

    // create

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response makeReservation(@Valid RentDTO rentDTO) {
        try {
            Rent rent = rentManager.add(rentDTO);
            return Response.status(CREATED).entity(rent).build();
        } catch (ObjectNotValidException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    // read

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        try {
            Client client = (Client) userManager.getUserByUuidOfType("Client", clientUuid);
            List<Rent> rents = rentManager.getRentsByClient(client);
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRents() {
        List<Rent> rents = rentManager.getAll();
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        try {
            Rent rent = rentManager.get(uuid);
            return Response.status(Response.Status.OK).entity(rent).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response modifyRent(@PathParam("uuid") UUID entityId, @Valid RentDTO rentDTO) {
        try {
            rentManager.update(entityId, rentDTO);
            return Response.status(OK).entity(rentDTO).build();
        } catch (ObjectNotValidException | TransactionalException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(NoResultException e) {
            return Response.status(NOT_FOUND).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        try {
            rentManager.remove(rentUuid);
            return Response.status(NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return Response.status(NO_CONTENT).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }
}
