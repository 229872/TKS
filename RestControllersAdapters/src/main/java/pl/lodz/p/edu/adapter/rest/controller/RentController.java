package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.dto.RentDTO;
import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.dto.users.*;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestBusinessLogicInterruptException;
import pl.lodz.p.edu.adapter.rest.exception.RestObjectNotValidException;


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
            rentService.add(rentDTO);
            return Response.status(CREATED).entity(rentDTO).build();
        } catch (RestObjectNotValidException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        try {
            ClientDTO clientDTO = (ClientDTO) userService.get(clientUuid);
            List<RentDTO> rentsDTO = rentService.getRentsByClient(clientDTO);
            return Response.status(Response.Status.OK).entity(rentsDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/equipment/{uuid}")
    public Response getEquipmentRents(@PathParam("uuid") UUID equipmentUuid) {
        try {
            EquipmentDTO equipmentDTO = equipmentService.get(equipmentUuid);
            List<RentDTO> rentsDTO = rentService.getRentsByEquipment(equipmentDTO);
            return Response.status(Response.Status.OK).entity(rentsDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRents() {
        List<RentDTO> rentsDTO = rentService.getAll();
        return Response.status(Response.Status.OK).entity(rentsDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        try {
            RentDTO rentDTO = rentService.get(uuid);
            return Response.status(Response.Status.OK).entity(rentDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response modifyRent(@PathParam("uuid") UUID entityId, @Valid RentDTO rentDTO) {
        try {
            RentDTO updatedRent = rentService.update(entityId, rentDTO);
            return Response.status(OK).entity(updatedRent).build();
        } catch (RestObjectNotValidException | TransactionalException e) {
            return Response.status(BAD_REQUEST).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        try {
            rentService.remove(rentUuid);
            return Response.status(NO_CONTENT).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NO_CONTENT).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).build();
        }
    }
}
