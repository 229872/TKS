package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.data.model.DTO.CredentialsDTO;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequestScoped
public class LoginController extends AbstractController {
    private String path = "login/";

    public LoginController() {
    }

    public String logIn(CredentialsDTO dto) {
        String body;
        try {
            body = om.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest request = buildPost(path, body);

        HttpResponse<String> response = send(request);
        if(response.statusCode() == 200) {
            return response.body();
        } else {
            return null;
        }
    }
}
