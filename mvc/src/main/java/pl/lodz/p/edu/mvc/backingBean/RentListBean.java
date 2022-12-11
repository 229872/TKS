package pl.lodz.p.edu.mvc.backingBean;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.DTO.MvcRentDTO;
import pl.lodz.p.edu.mvc.controller.RentController;

import javax.faces.bean.SessionScoped;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class RentListBean {
    @Inject
    private RentController rentController;

    public RentListBean() {}

    @PostConstruct
    public void initClients() {
        rents = rentController.getAll();
    }

    private String searchParam;

    public String getSearchParam() {
        return searchParam;
    }

    public void setSearchParam(String searchParam) {
        this.searchParam = searchParam;
    }

    private List<MvcRentDTO> rents;

    public List<MvcRentDTO> getRents() {
        return rents;
    }

    public void delete(Rent rent) {
        rentController.delete(rent);
    }
}