package pl.lodz.p.edu.mvc.beans;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;
import pl.lodz.p.edu.mvc.request.Request;

import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class ListClientBean extends AbstractBean {
    private static String res = "clients";

    public ListClientBean() {
        try {
            HttpRequest request = Request.buildGet(res);
            HttpResponse<String> response = send(request);
            listAll = Arrays.asList(om.readValue(response.body(), Client[].class));
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Client> getListAll() {
        return listAll;
    }
    private List<Client> listAll;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    private String selected;




}
