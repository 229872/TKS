package pl.lodz.p.edu.mvc.beans;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;

public abstract class AbstractBean {
    protected HttpClient client = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_1_1)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();
    protected HttpRequest request;

    protected ObjectMapper om;

    private static String base = "http://localhost:8080/rest/api/";

    public void buildRequestGet(String path) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildRequestPost(String path, String body) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildRequestPut(String path, String body) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void buildRequestDelete(String path) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


    protected AbstractBean(String res) {
        buildRequestGet(res);
        om = new ObjectMapper();
    }


}
