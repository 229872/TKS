package pl.lodz.p.edu.mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import pl.lodz.p.edu.mvc.backingBean.JwtSessionBean;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class AbstractController {

    private final String base = "http://localhost:8080/rest/api/";
    protected ObjectMapper om;

    @Inject
    private JwtSessionBean sessionBean;

    private HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    protected AbstractController() {
        om = new ObjectMapper();
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

    protected HttpRequest buildGet(String path) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .header("AUTHORIZATION", "BEARER " + sessionBean.getJwtToken())
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpRequest buildPost(String path, String body) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .header("AUTHORIZATION", "BEARER " + sessionBean.getJwtToken())
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpRequest buildPut(String path, String body) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .header("AUTHORIZATION", "BEARER " + sessionBean.getJwtToken())
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpRequest buildPutUser(String path, String body, String ifMatch) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .header("AUTHORIZATION", "BEARER " + sessionBean.getJwtToken())
                    .header("IF-MATCH", ifMatch)
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpRequest buildDelete(String path) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .header("AUTHORIZATION", "BEARER " + sessionBean.getJwtToken())
                    .DELETE()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
