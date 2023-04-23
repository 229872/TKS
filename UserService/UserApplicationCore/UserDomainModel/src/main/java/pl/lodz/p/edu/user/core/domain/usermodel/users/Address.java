package pl.lodz.p.edu.user.core.domain.usermodel.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Address {
    private String city;
    private String street;
    private String streetNr;
}