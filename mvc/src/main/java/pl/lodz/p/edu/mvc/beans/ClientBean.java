//package pl.lodz.p.edu.mvc.beans;
//
//import jakarta.inject.Named;
//import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
//import pl.lodz.p.edu.data.model.users.Client;
//
//import javax.faces.bean.SessionScoped;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.io.IOException;
//import java.net.*;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.List;
//
//@Named
//@SessionScoped
//public class ClientBean {
//
//    private static String url = "http://localhost:8080/rest/api";
//
////    protected HttpClient client = HttpClient.newBuilder()
////            .version(HttpClient.Version.HTTP_1_1)
////            .followRedirects(HttpClient.Redirect.NORMAL)
////            .connectTimeout(Duration.ofSeconds(20))
////            .build();
//    javax.ws.rs.client.Client client = ClientBuilder.newClient();
//
//    WebTarget target = client.target(url);
//
//    WebTarget clientTarget = target.path("clients");
//
//    private List<Client> clients;
//
//    public ClientBean() {
////        Invocation.Builder invocationBuilder = clientTarget.request(MediaType.APPLICATION_JSON);
////        Response response = invocationBuilder.get();
////        System.out.println(response);
//
////        try {
////            HttpRequest request = HttpRequest.newBuilder()
////                    .uri(new URI(url))
////                    .header("Content-Type", "application/json")
////                    .GET()
////                    .build();
////            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
////        }  catch (URISyntaxException ignored) {
////
////        } catch (IOException | InterruptedException e) {
////            throw new RuntimeException(e);
////        }
//    }
//
//    public List<Client> getClients() {
//        return clients;
//    }
//}
