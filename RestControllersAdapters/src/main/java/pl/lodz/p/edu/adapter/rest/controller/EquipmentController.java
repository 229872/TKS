package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.dto.EquipmentDTO;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/equipment")
public class EquipmentController {

    @Inject
    private EquipmentService equipmentService;
    @Inject
    private RentService rentService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(@Valid EquipmentDTO equipmentDTO) {
        try {
            equipmentService.add(equipmentDTO);
            return Response.status(CREATED).entity(equipmentDTO).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<EquipmentDTO> equipment = equipmentService.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailable() {
        List<EquipmentDTO> allEquipment = equipmentService.getAll();
        List<EquipmentDTO> availableEquipment = allEquipment.stream()
                .filter(equipment -> rentService.checkEquipmentAvailable(equipment, LocalDateTime.now()))
                .toList();

        return Response.status(OK).entity(availableEquipment).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        try {
            EquipmentDTO equipment = equipmentService.get(uuid);
            return Response.status(Response.Status.OK).entity(equipment).build();
        } catch(EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{entityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("entityId") UUID entityId, @Valid EquipmentDTO equipmentDTO) {
        try {
            equipmentService.update(entityId, equipmentDTO);
            return Response.status(OK).entity(equipmentDTO).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).build();
        } catch(EntityNotFoundException e) {
            return Response.status(NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response unregisterEquipment(@PathParam("uuid") UUID uuid) {
        try {
            equipmentService.remove(uuid);
            return Response.status(NO_CONTENT).build();
        } catch (RestConflictException e) {
            return Response.status(CONFLICT).build();
        }
    }
}
