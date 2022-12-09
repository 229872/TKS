package pl.lodz.p.edu.mvc.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.net.http.HttpResponse;
import java.util.Map;

@Named
@ConversationScoped
public class ClientBean extends AbstractBean implements Serializable {

    private static String res = "clients/";

    private Client client_;


    public ClientBean() {
        super(res);
    }

    @PostConstruct
    public void init() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> params = context.getRequestParameterMap();
        String id = params.get("uuid");
        if(id == null) {
            try {
                context.redirect("listClients.xhtml");
                return;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(id);
        buildRequestGet(res + id);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            client_ = om.readValue(response.body(), Client.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public Client getClient_() {
        return client_;
    }

    private int errCode;

    public int getErrCode() {
        return errCode;
    }

    public void update() {
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            String body = om.writeValueAsString(client_);
            String clientId = client_.getEntityId().toString();
            buildRequestPut(res + clientId, body);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            errCode = response.statusCode();
            // todo komunikat
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void activate() {
        String clientId = client_.getEntityId().toString();
        buildRequestPut(res + clientId + "/activate", "");
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            errCode = response.statusCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void deactivate() {
        String clientId = client_.getEntityId().toString();
        buildRequestPut(res + clientId + "/deactivate", "");
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            errCode = response.statusCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
