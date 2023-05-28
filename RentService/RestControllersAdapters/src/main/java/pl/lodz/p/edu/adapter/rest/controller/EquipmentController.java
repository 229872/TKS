package pl.lodz.p.edu.adapter.rest.controller;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import pl.lodz.p.edu.adapter.rest.api.EquipmentService;
import pl.lodz.p.edu.adapter.rest.api.RentService;
import pl.lodz.p.edu.adapter.rest.dto.input.EquipmentInputDTO;
import pl.lodz.p.edu.adapter.rest.dto.output.EquipmentOutputDTO;
import pl.lodz.p.edu.adapter.rest.exception.ObjectNotFoundRestException;
import pl.lodz.p.edu.adapter.rest.exception.RestConflictException;
import pl.lodz.p.edu.adapter.rest.exception.RestIllegalModificationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.ws.rs.core.Response.Status.*;

@Path("/equipment")
@Stateless
public class EquipmentController {

    @Inject
    private EquipmentService equipmentService;
    @Inject
    private RentService rentService;

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEquipment(@Valid EquipmentInputDTO equipmentInputDTO) {
        try {
            EquipmentOutputDTO equipmentOutputDTO = equipmentService.add(equipmentInputDTO);
            return Response.status(CREATED).entity(equipmentOutputDTO).build();
        } catch(NullPointerException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllEquipment() {
        List<EquipmentOutputDTO> equipment = equipmentService.getAll();
        return Response.ok(equipment).build();
    }

    @GET
    @Path("/available")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(
            name = "getAvailableEquipment",
            absolute = true,
            unit = MetricUnits.MINUTES
    )
    public Response getAllAvailable() {
        List<EquipmentOutputDTO> allEquipment = equipmentService.getAll();
        List<EquipmentOutputDTO> availableEquipment = allEquipment.stream()
                .filter(equipment -> rentService.checkEquipmentAvailable(equipment, LocalDateTime.now()))
                .toList();

        return Response.ok(availableEquipment).build();
    }

    @GET
    @Path("/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEquipment(@PathParam("uuid") UUID uuid) {
        try {
            EquipmentOutputDTO equipment = equipmentService.get(uuid);
            return Response.ok(equipment).build();
        } catch(ObjectNotFoundRestException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{entityId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEquipment(@PathParam("entityId") UUID entityId, @Valid EquipmentInputDTO equipmentInputDTO) {
        try {
            EquipmentOutputDTO updated = equipmentService.update(entityId, equipmentInputDTO);
            return Response.ok(updated).build();
        } catch (RestIllegalModificationException e) {
            return Response.status(BAD_REQUEST).entity(e.getMessage()).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{uuid}")
    public Response unregisterEquipment(@PathParam("uuid") UUID uuid) {
        try {
            equipmentService.remove(uuid);
            return Response.status(NO_CONTENT).build();
        } catch (RestConflictException e) {
            return Response.status(CONFLICT).entity(e.getMessage()).build();
        } catch (ObjectNotFoundRestException e) {
            return Response.status(NO_CONTENT).entity(e.getMessage()).build();
        }
    }
}
