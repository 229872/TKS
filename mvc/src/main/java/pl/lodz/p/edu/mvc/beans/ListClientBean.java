package pl.lodz.p.edu.mvc.beans;

import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.users.Client;

import javax.faces.bean.SessionScoped;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class ListClientBean extends AbstractBean {
    private static String res = "clients";

    public ListClientBean() {
        super(res);
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            listAll = Arrays.asList(om.readValue(response.body(), Client[].class));
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); // todo komunikat
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
