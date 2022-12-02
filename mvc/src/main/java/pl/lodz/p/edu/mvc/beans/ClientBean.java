package pl.lodz.p.edu.mvc.beans;

import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;

import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ClientBean {

    private static URL url = null;
    private HttpURLConnection conn;

    public ClientBean() {
        try {
            url = new URL("http://localhost:8080/rest/api/clients");
        } catch (MalformedURLException ignored) {}

    }

    public List<ClientDTO> getAllActive() {
        try {
            conn = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return new ArrayList<>();
    }
}
