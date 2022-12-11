package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import pl.lodz.p.edu.data.model.DTO.RentDTO;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.request.Request;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.*;

public class RentController extends AbstractController {

    private static final String path = "rents/";

    public List<MvcRentDTO> getClientRents(String clientId) {
        HttpRequest request = Request.buildGet(path + "client/" + clientId);
        HttpResponse<String> response = send(request);
        try {
            return Arrays.asList(om.readValue(response.body(), MvcRentDTO[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MvcRentDTO> getEquipmentRents(String equipmentId) {
        HttpRequest request = Request.buildGet(path + "equipment/" + equipmentId);
        HttpResponse<String> response = send(request);
        try {
            return Arrays.asList(om.readValue(response.body(), MvcRentDTO[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MvcRentDTO> getAll() {
        HttpRequest request = Request.buildGet(path);
        HttpResponse<String> response = send(request);
        try {
            return Arrays.asList(om.readValue(response.body(), MvcRentDTO[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MvcRentDTO get(String uuid) {
        HttpRequest request = buildGet(path + uuid);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public MvcRentDTO update(MvcRentDTO mvcRentDTO) {
        //FIXME I HAVE NO F IDEA HOW THESE SHOULD WORK
        String body;
        try {
            body = om.writeValueAsString(mvcRentDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        HttpRequest request = buildPut(path + mvcRentDTO.getEntityId(), body);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public MvcRentDTO create(MvcRentDTO mvcRentDTO) {
        //FIXME I HAVE NO F IDEA HOW THESE SHOULD WORK
        String body;
        try {
            body = om.writeValueAsString(mvcRentDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage()); // todo komunikat
        }
        HttpRequest request = buildPost(path, body);
        HttpResponse<String> response = send(request);

        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public void delete(Rent rent) {
        HttpRequest request = buildDelete(path + rent.getEntityId().toString());
        HttpResponse<String> response = send(request);
    }
}
