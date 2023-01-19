package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import pl.lodz.p.edu.data.model.DTO.CredentialsNewPasswordDTO;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ChangePasswordController extends AbstractController {
    private String path = "changePassword/";

    public ChangePasswordController() {}

    public boolean change(CredentialsNewPasswordDTO credentials) {
        String body;
        try {
            body = om.writeValueAsString(credentials);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest request = buildPut(path, body);

        HttpResponse<String> response = send(request);
        return response.statusCode() == 200;
    }
}
