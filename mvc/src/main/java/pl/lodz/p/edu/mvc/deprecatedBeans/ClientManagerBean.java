package pl.lodz.p.edu.mvc.deprecatedBeans;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.DTO.users.ClientDTO;
import pl.lodz.p.edu.data.model.users.Client;

import javax.faces.bean.SessionScoped;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static pl.lodz.p.edu.mvc.request.Request.buildPut;

@Named
@SessionScoped
public class ClientManagerBean extends AbstractBean {
    private static String res = "clients";

    public ClientManagerBean() {

    }
    public ClientDTO getNewClient() {
        return newClient;
    }

    private ClientDTO newClient;

    public void saveNewClient() {
        String body;
        try {
            body = om.writeValueAsString(newClient);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e); // todo komunikat
        }
        HttpRequest request = buildPut(res, body);
        HttpResponse<String> response = send(request);
        errCode = response.statusCode(); // komunikat na podstawie tego
    }

    public int getErrCode() {
        return errCode;
    }

    private int errCode;


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
