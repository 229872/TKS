package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.request.Request;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.buildGet;
import static pl.lodz.p.edu.mvc.request.Request.buildPut;

@Named
@RequestScoped
public class ClientController extends UserController<Client, ClientDTO> {

    private static final String path = "clients/";


    public ClientController() {
        super(path, Client.class, Client[].class, ClientDTO.class);
    }
}
