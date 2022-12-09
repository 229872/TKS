package pl.lodz.p.edu.mvc.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public abstract class AbstractBean {
    private HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    protected ObjectMapper om;

    protected AbstractBean() {
        om = new ObjectMapper();
    }

    private ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    protected String loadUuid() {
        ExternalContext context = getContext();
        Map<String, String> params = context.getRequestParameterMap();
        return params.get("uuid");
    }

    protected void redirect(String page) {
        ExternalContext context = getContext();
        try {
            context.redirect(page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpResponse<String> send(HttpRequest request) {
        try {
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
