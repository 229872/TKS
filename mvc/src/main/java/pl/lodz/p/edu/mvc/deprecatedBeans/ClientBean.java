package pl.lodz.p.edu.mvc.deprecatedBeans;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static pl.lodz.p.edu.mvc.request.Request.buildGet;
import static pl.lodz.p.edu.mvc.request.Request.buildPut;

@Named
@ConversationScoped
public class ClientBean extends AbstractBean implements Serializable {

    // todo każde rzucanie wyjatku trzeba opakować

    private static String res = "clients/";

    private Client client;


    @PostConstruct
    public void init() {
        String id = loadUuid();
        if(id == null) {
            return;
        }
        HttpRequest request = buildGet(res + id);
        try {
            HttpResponse<String> response = send(request);
            client = om.readValue(response.body(), Client.class);
        } catch (IOException e) {
            throw new RuntimeException(e); // todo komunikat
        }
    }

    public Client getClient() {
        return client;
    }

    private int errCode;

    public int getErrCode() {
        return errCode;
    }

    public void update() {
        String body;
        try {
            body = om.writeValueAsString(client);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        String clientId = client.getEntityId().toString();
        HttpRequest request = buildPut(res + clientId, body);
        HttpResponse<String> response = send(request);
        errCode = response.statusCode(); // komunikat na podstawie tego
    }

    public void activate() {
        String clientId = client.getEntityId().toString();
        HttpRequest request = buildPut(res + clientId + "/activate", "");
        HttpResponse<String> response = send(request);
        errCode = response.statusCode();
    }

    public void deactivate() {
        String clientId = client.getEntityId().toString();
        HttpRequest request = buildPut(res + clientId + "/deactivate", "");
        HttpResponse<String> response = send(request);
        errCode = response.statusCode();
    }
}
