package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.rest.DTO.users.AdminDTO;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    @Column(name = "favourite_ice_cream")
    @NotEmpty
    private String favouriteIceCream;

    public Admin() {

    }

    public Admin(AdminDTO adminDTO) {
        this.merge(adminDTO);
        this.userType = ADMIN_TYPE;
    }

    public Admin(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
        this.userType = ADMIN_TYPE;
    }

    public boolean verify() {
        return super.verify() && !favouriteIceCream.isEmpty();
    }

    public void merge(AdminDTO adminDTO) {
        this.setLogin(adminDTO.getLogin());
        this.setPassword(adminDTO.getPassword());
        this.favouriteIceCream = adminDTO.getFavouriteIceCream();
    }


    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

}
