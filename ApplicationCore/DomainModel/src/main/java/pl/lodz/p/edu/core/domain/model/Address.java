package pl.lodz.p.edu.core.domain.model;


public class Address {

    private String city;

    private String street;

    private String streetNr;

    public Address(String city, String street, String streetNr) {
        this.city = city;
        this.street = street;
        this.streetNr = streetNr;
    }

    public Address() {}

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