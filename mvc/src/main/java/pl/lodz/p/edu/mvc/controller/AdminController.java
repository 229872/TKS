package pl.lodz.p.edu.mvc.controller;

import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.data.model.users.Admin;

@RequestScoped
public class AdminController extends UserController<Admin, AdminDTO> {

    private static final String path = "admins/";


    public AdminController() {
        super(path, Admin.class, Admin[].class, AdminDTO.class);
    }
}
