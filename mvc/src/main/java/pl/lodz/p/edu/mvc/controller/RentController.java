package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import pl.lodz.p.edu.data.model.DTO.RentDTO;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.request.Request;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.*;

public class RentController extends AbstractController {

    private static final String path = "rents/";

    public List<MvcRentDTO> getClientRents(String clientId) {
        HttpRequest request = this.buildGet(path + "client/" + clientId);
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
        HttpRequest request = this.buildGet(path + "equipment/" + equipmentId);
        HttpResponse<String> response = send(request);
        if(response.statusCode() == 404) {
            throw new NotFoundException();
        }
        try {
            return Arrays.asList(om.readValue(response.body(), MvcRentDTO[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MvcRentDTO> getAll() {
        HttpRequest request = this.buildGet(path);
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
        HttpRequest request = this.buildGet(path + uuid);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public MvcRentDTO update(MvcRentDTO mvcRentDTO) {
        String body;
        try {
            body = om.writeValueAsString(mvcRentDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        HttpRequest request = this.buildPut(path + mvcRentDTO.getEntityId(), body);
        HttpResponse<String> response = send(request);
        if (response.statusCode() == 404) {
            throw new NotFoundException();
        }
        if (response.statusCode() == 400) {
            throw new BadRequestException();
        }

        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public MvcRentDTO create(MvcRentDTO mvcRentDTO) {
        RentDTO rentDTO = mvcRentDTO.toRentDTO();
        String body;
        try {
            body = om.writeValueAsString(rentDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage()); // todo komunikat
        }
        HttpRequest request = this.buildPost(path, body);
        HttpResponse<String> response = send(request);

        if (response.statusCode() == 409) {
            throw new ClientErrorException(409);
        }
        if (response.statusCode() == 400) {
            throw new BadRequestException();
        }

        try {
            return om.readValue(response.body(), MvcRentDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public void delete(String id) {
        HttpRequest request = this.buildDelete(path + id);
        HttpResponse<String> response = send(request);
    }
}
