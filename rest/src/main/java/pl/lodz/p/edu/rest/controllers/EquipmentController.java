package pl.lodz.p.edu.rest.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.lodz.p.edu.rest.exception.IllegalModificationException;
import pl.lodz.p.edu.rest.exception.ConflictException;
import pl.lodz.p.edu.rest.managers.api.EquipmentManager;
import pl.lodz.p.edu.data.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.rest.managers.api.RentManager;
import pl.lodz.p.edu.rest.util.DataFaker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;


@Path("/equipment")
public class EquipmentController {

    @Inject
    private EquipmentManager equipmentManager;
    @Inject
    private RentManager rentManager;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(@Valid EquipmentDTO equipmentDTO) {
        try {
            Equipment equipment = new Equipment(equipmentDTO);
            equipmentManager.add(equipment);
            return Response.status(CREATED).entity(equipment).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<Equipment> equipment = equipmentManager.getAll();
        return Response.status(Response.Status.OK).entity(equipment).build();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAvailable() {
        List<Equipment> allEquipment = equipmentManager.getAll();
        List<Equipment> availableEquipment = allEquipment.stream()
                .filter(equipment -> rentManager.checkEquipmentAvailable(equipment, LocalDateTime.now()))
                .toList();

        return Response.status(OK).entity(availableEquipment).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        try {
            Equipment equipment = equipmentManager.get(uuid);
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
            equipmentManager.update(entityId, equipmentDTO);
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
            equipmentManager.remove(uuid);
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
            equipmentManager.add(e);
        } catch(Exception ex) {
            return null;
        }
        return e;
    }
}
