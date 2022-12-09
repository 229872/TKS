package pl.lodz.p.edu.mvc.request;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

public class Request {

    public static final String base = "http://localhost:8080/rest/api/";

    public static HttpRequest buildGet(String path) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpRequest buildPost(String path, String body) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpRequest buildPut(String path, String body) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static HttpRequest buildDelete(String path) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(base + path))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
