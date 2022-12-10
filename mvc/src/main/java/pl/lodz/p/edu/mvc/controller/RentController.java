package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import pl.lodz.p.edu.data.model.DTO.RentDTO;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.mvc.MvcRentDTO;
import pl.lodz.p.edu.mvc.request.Request;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

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
}
