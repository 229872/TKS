package entity.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import pl.lodz.p.edu.rest.DTO.users.AdminDTO;

@Entity
@DiscriminatorValue("admin")
public class AdminEnt extends UserEnt {
    @Column(name = "favourite_ice_cream")
    @NotEmpty
    private String favouriteIceCream;

    public AdminEnt() {

    }

    public AdminEnt(AdminDTO adminDTO) {
        this.merge(adminDTO);
        this.userType = ADMIN_TYPE;
    }

    public AdminEnt(String login, String password, String favouriteIceCream) {
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
