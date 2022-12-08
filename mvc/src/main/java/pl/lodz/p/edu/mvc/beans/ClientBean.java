package pl.lodz.p.edu.mvc.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Named
@RequestScoped
public class ClientBean extends AbstractBean {

    private static String res = "clients";

    private Client client_;


    public ClientBean() {
        super(res);
    }

    public void init() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id = params.get("uuid");
        try {
            buildRequest(res + "/" + id);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            client_ = om.readValue(response.body(), Client.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public Client getClient_() {
        return client_;
    }
}
