package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.inject.Inject;
import jakarta.transaction.TransactionalException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.UserService;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.dto.input.RentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.input.users.*;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
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
    public Response makeReservation(@Valid RentInputDTO rentInputDTO) {
        try {
            rentService.add(rentInputDTO);
            return Response.status(CREATED).entity(rentInputDTO).build();
        } catch (RestObjectNotValidException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/client/{uuid}")
    public Response getClientRents(@PathParam("uuid") UUID clientUuid) {
        try {
            ClientInputDTO clientDTO = (ClientInputDTO) userService.get(clientUuid);
            List<RentInputDTO> rentsDTO = rentService.getRentsByClient(clientDTO);
            return Response.ok(rentsDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/equipment/{uuid}")
    public Response getEquipmentRents(@PathParam("uuid") UUID equipmentUuid) {
        try {
            EquipmentOutputDTO equipmentOutputDTO = equipmentService.get(equipmentUuid);
            List<RentInputDTO> rentsDTO = rentService.getRentsByEquipment(equipmentOutputDTO);
            return Response.ok(rentsDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRents() {
        List<RentInputDTO> rentsDTO = rentService.getAll();
        return Response.ok(rentsDTO).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response getRent(@PathParam("uuid") UUID uuid) {
        try {
            RentInputDTO rentInputDTO = rentService.get(uuid);
            return Response.ok(rentInputDTO).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    public Response modifyRent(@PathParam("uuid") UUID entityId, @Valid RentInputDTO rentInputDTO) {
        try {
            RentInputDTO updatedRent = rentService.update(entityId, rentInputDTO);
            return Response.ok(updatedRent).build();
        } catch (RestObjectNotValidException | TransactionalException e) {
            return Response.status(BAD_REQUEST).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response cancelReservation(@PathParam("uuid") UUID rentUuid) {
        try {
            rentService.remove(rentUuid);
            return Response.status(NO_CONTENT).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NO_CONTENT).entity(e.getMessage()).build();
        } catch (RestBusinessLogicInterruptException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        }
    }
}
