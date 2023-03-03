package pl.lodz.p.edu.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.exception.ConflictException;
import pl.lodz.p.edu.exception.IllegalModificationException;
import pl.lodz.p.edu.service.api.EquipmentService;
import pl.lodz.p.edu.DTO.EquipmentDTO;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.service.api.RentService;
import pl.lodz.p.edu.util.DataFaker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;


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
            Equipment equipment = new Equipment(equipmentDTO);
            equipmentService.add(equipment);
            return Response.status(CREATED).entity(equipment).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<Equipment> equipment = equipmentService.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailable() {
        List<Equipment> allEquipment = equipmentService.getAll();
        List<Equipment> availableEquipment = allEquipment.stream()
                .filter(equipment -> rentService.checkEquipmentAvailable(equipment, LocalDateTime.now()))
                .toList();

        return Response.status(OK).entity(availableEquipment).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        try {
            Equipment equipment = equipmentService.get(uuid);
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
        } catch (IllegalModificationException e) {
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
        } catch (ConflictException e) {
            return Response.status(CONFLICT).build();
        }
    }


    @POST
    @Path("/addFake")
    @Produces(MediaType.APPLICATION_JSON)
    public Equipment addFakeEquipment() {
        Equipment e = DataFaker.getEquipment();
        try {
            equipmentService.add(e);
        } catch(Exception ex) {
            return null;
        }
        return e;
    }
}
