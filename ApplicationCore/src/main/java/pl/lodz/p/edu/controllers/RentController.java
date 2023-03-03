package pl.lodz.p.edu.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.exception.ObjectNotValidException;
import pl.lodz.p.edu.DTO.RentDTO;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.model.Rent;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.service.api.EquipmentService;
import pl.lodz.p.edu.service.api.RentService;
import pl.lodz.p.edu.service.api.UserService;

import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/rents")
public class RentController {

    @Inject
    private RentService rentService;

    @Inject
    private UserService userService;

    @Inject
    private EquipmentService equipmentService;

    // create

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response makeReservation(@Valid RentDTO rentDTO) {
        try {
            Rent rent = rentService.add(rentDTO);
            return Response.status(CREATED).entity(rent).build();
        } catch (ObjectNotValidException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        try {
            Client client = (Client) userService.getUserByUuidOfType("Client", clientUuid);
            List<Rent> rents = rentService.getRentsByClient(client);
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (NoResultException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/equipment/{uuid}")
    public Response getEquipmentRents(@PathParam("uuid") UUID equipmentUuid) {
        try {
            Equipment equipment = equipmentService.get(equipmentUuid);
            List<Rent> rents = rentService.getRentByEq(equipment);
            return Response.status(Response.Status.OK).entity(rents).build();
        } catch (NoResultException | EntityNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRents() {
        List<Rent> rents = rentService.getAll();
        return Response.status(Response.Status.OK).entity(rents).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        try {
            Rent rent = rentService.get(uuid);
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
            rentService.update(entityId, rentDTO);
            return Response.status(OK).entity(rentDTO).build();
        } catch (ObjectNotValidException | TransactionalException e) {
            return Response.status(BAD_REQUEST).build();
        } catch (NoResultException e) {
            return Response.status(NOT_FOUND).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        try {
            rentService.remove(rentUuid);
            return Response.status(NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return Response.status(NO_CONTENT).build();
        } catch (BusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }
}
