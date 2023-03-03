package pl.lodz.p.edu.adapter.repository.clients.data;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.FIELD)
public class AddressEnt {

    @NotNull
    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "streetNr")
    private String streetNr;

    public AddressEnt(String city, String street, String streetNr) {
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public AddressEnt() {}

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getStreetNr() {
        return streetNr;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreetNr(String streetNr) {
        this.streetNr = streetNr;
    }

    String getAddressInfo() {
        final StringBuilder sb = new StringBuilder("Rent{");
        sb.append("Miasto=").append(getCity());
        sb.append("Ulica=").append(getStreet());
        sb.append("Numer mieszkania=").append(getStreetNr());

        return sb.toString();
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", streetNr='" + streetNr + '\'' +
                '}';
    }
}