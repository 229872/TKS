package pl.lodz.p.edu.data.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

@Embeddable
@Access(AccessType.FIELD)
public class Address {

    @NotNull
    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @NotNull
    @Column(name = "streetNr")
    private String streetNr;

    public Address(String city, String street, String streetNr) {
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    protected Address() {}

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

    public boolean verify() {
        return !(city == null || street == null || streetNr == null);
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