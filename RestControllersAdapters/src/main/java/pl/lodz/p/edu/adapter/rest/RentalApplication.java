package pl.lodz.p.edu.adapter.rest;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@DeclareRoles({"ADMIN", "EMPLOYEE", "CLIENT", "GUEST"})
public class RentalApplication extends Application {
}