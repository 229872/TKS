package pl.lodz.p.edu.rest.data.model.DTO.users;

import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.rest.data.model.users.Admin;

public class AdminDTO extends UserDTO {

    @NotEmpty
    private String favouriteIceCream;

    public AdminDTO() {}

    public AdminDTO(Admin a) {
        super(a);
        this.favouriteIceCream = a.getFavouriteIceCream();
    }

    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

    @Override
    public String toString() {
        return "AdminDTO{" +
                "favouriteIceCream='" + favouriteIceCream + '\'' +
                "} " + super.toString();
    }
}
