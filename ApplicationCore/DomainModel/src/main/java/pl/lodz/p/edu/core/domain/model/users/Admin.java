package pl.lodz.p.edu.core.domain.model.users;

public class Admin extends User {

    private String favouriteIceCream;

    public Admin() {

    }

    public Admin(String login, String password, String favouriteIceCream) {
        super(login, password);
        this.favouriteIceCream = favouriteIceCream;
        this.userType = UserType.ADMIN;
    }

    public void merge(Admin admin) {
        this.setLogin(admin.getLogin());
        this.setPassword(admin.getPassword());
        this.favouriteIceCream = admin.getFavouriteIceCream();
    }


    public String getFavouriteIceCream() {
        return favouriteIceCream;
    }

    public void setFavouriteIceCream(String favouriteIceCream) {
        this.favouriteIceCream = favouriteIceCream;
    }

}
