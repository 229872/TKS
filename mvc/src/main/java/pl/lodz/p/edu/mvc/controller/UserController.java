package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import pl.lodz.p.edu.mvc.request.Request;

import javax.swing.*;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.*;

public class UserController<T, Tdto> extends AbstractController {

    private String path;
    private final Class<T> TType;
    private final Class<T[]> TListType;
    private final Class<Tdto> TdtoType;

    public UserController(String path, Class<T> TType, Class<T[]> TListType, Class<Tdto> TdtoType) {
        this.path = path;
        this.TType = TType;
        this.TListType = TListType;
        this.TdtoType = TdtoType;
    }

    public List<T> getAll() {
        HttpRequest request = Request.buildGet(path);
        HttpResponse<String> response = send(request);

        // fixme check for status codes?
        try {
            return Arrays.asList(om.readValue(response.body(), TListType));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<T> search(String login) {
        HttpRequest request = Request.buildGet(path + "?login=" + login);
        HttpResponse<String> response = send(request);
        if(response.statusCode() != 200) {
            throw new RuntimeException(response.toString());
        }
        try {
            return Arrays.asList(om.readValue(response.body(), TListType));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public T get(String uuid) {
        HttpRequest request = buildGet(path + uuid);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), TType);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public Tdto update(String id, Tdto user) {
        String body;
        try {
            body = om.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        HttpRequest request = buildPut(path + id, body);
        HttpResponse<String> response = send(request);
        try {
            return om.readValue(response.body(), TdtoType);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public T create(Tdto user) {
        String body;
        try {
            body = om.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage()); // todo komunikat
        }
        HttpRequest request = buildPost(path, body);
        HttpResponse<String> response = send(request);

        try {
            return om.readValue(response.body(), TType);
        } catch (IOException e) {
            throw new RuntimeException(e.getCause()); // todo komunikat
        }

    }

    public void activate(String id) {
        HttpRequest request = buildPut(path + id + "/activate", "");
        HttpResponse<String> response = send(request);
    }

    public void deactivate(String id) {
        HttpRequest request = buildPut(path + id + "/deactivate", "");
        HttpResponse<String> response = send(request);
    }

}
