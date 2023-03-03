package clients.data.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;

@Entity
@DiscriminatorValue("admin")
public class AdminEnt extends UserEnt {
    @Column(name = "favourite_ice_cream")
    @NotEmpty
    private String favouriteIceCream;

    public AdminEnt() {

    }

    public AdminEnt(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
        this.userType = ADMIN_TYPE;
    }

    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

}
