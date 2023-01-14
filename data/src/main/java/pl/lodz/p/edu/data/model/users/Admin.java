package pl.lodz.p.edu.data.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.data.model.DTO.users.AdminDTO;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    @Column(name = "favourite_ice_cream")
    @NotEmpty
    private String favouriteIceCream;

    public Admin() {
        this.role = UserType.ADMIN;
    }

    public Admin(AdminDTO adminDTO) {
        this.merge(adminDTO);
        this.role = UserType.ADMIN;
    }

    public Admin(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
        this.role = UserType.ADMIN;
    }

    public boolean verify() {
        return super.verify() && !favouriteIceCream.isEmpty();
    }

    public void merge(AdminDTO adminDTO) {
        this.setLogin(adminDTO.getLogin());
        this.setPassword(adminDTO.getPassword());
        this.favouriteIceCream = adminDTO.getFavouriteIceCream();
        this.role = UserType.ADMIN;
    }


    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

    public UserType getRole() {
        return role;
    }

}
