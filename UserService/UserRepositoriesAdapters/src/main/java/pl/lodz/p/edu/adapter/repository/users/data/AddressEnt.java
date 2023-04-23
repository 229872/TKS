package pl.lodz.p.edu.adapter.repository.users.data;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressEnt {

    private String city;
    private String street;
    private String streetNr;
}