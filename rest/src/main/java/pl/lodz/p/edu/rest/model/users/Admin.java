package pl.lodz.p.edu.rest.model.users;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import pl.lodz.p.edu.rest.model.DTO.users.AdminDTO;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {
    @Column(name = "favourite_ice_cream")
    private String favouriteIceCream;

    public Admin() {
    }

    public Admin(AdminDTO adminDTO) throws ObjectNotValidException {
        this.merge(adminDTO);
    }

    public Admin(String login, String favouriteIceCream) throws ObjectNotValidException {
        super(login);
        this.favouriteIceCream = favouriteIceCream;
    }

    public boolean verify() {
        return super.verify() && !favouriteIceCream.isEmpty();
    }

    public void merge(AdminDTO adminDTO) {
        this.setLogin(adminDTO.getLogin());
        this.favouriteIceCream = adminDTO.getFavouriteIceCream();
    }



    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }
}
