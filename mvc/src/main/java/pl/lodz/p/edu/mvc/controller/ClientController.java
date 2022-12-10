package pl.lodz.p.edu.mvc.controller;

import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;

@RequestScoped
public class ClientController extends UserController<Client, ClientDTO> {

    private static final String path = "clients/";


    public ClientController() {
        super(path, Client.class, Client[].class, ClientDTO.class);
    }
}
