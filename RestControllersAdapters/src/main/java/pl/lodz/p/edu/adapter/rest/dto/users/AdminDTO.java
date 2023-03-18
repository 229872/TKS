package pl.lodz.p.edu.adapter.rest.dto.users;

import jakarta.validation.constraints.NotEmpty;

public class AdminDTO extends UserDTO {

    @NotEmpty(message = "Admin must have ice creams")
    private String favouriteIceCream;

    public AdminDTO() {
        super(UserTypeDTO.ADMIN);
    }

    public AdminDTO(String login, String password, String favouriteIceCream) {
        super(login, password, UserTypeDTO.ADMIN);
        this.favouriteIceCream = favouriteIceCream;
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
