package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import pl.lodz.p.edu.data.model.DTO.EquipmentDTO;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.mvc.request.Request;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.*;

public class EquipmentController extends AbstractController {
    
    public static final String path = "equipment/";
    
    public Equipment get(String uuid) {
        HttpRequest request = buildGet(path + uuid);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), Equipment.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public List<Equipment> getAll() {
        HttpRequest request = Request.buildGet(path);
        HttpResponse<String> response = send(request);

        // fixme check for status codes?
        try {
            return Arrays.asList(om.readValue(response.body(), Equipment[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Equipment> getAvailable() {
        HttpRequest request = Request.buildGet(path + "available");
        HttpResponse<String> response = send(request);

        // fixme check for status codes?
        try {
            return Arrays.asList(om.readValue(response.body(), Equipment[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EquipmentDTO update(String id, EquipmentDTO equipment) {
        String body;
        try {
            body = om.writeValueAsString(equipment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        HttpRequest request = buildPut(path + id, body);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), EquipmentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public Equipment create(EquipmentDTO equipment) {
        String body;
        try {
            body = om.writeValueAsString(equipment);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage()); // todo komunikat
        }
        HttpRequest request = buildPost(path, body);
        HttpResponse<String> response = send(request);

        try {
            return om.readValue(response.body(), Equipment.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public int delete(Equipment equipment) {
        HttpRequest request = buildDelete(path + equipment.getEntityId().toString());
        return send(request).statusCode();

    }
}
