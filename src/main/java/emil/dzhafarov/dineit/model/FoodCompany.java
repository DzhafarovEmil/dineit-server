package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class FoodCompany extends Business {

    private static final long serialVersionUID = 999997732242241606L;

    @Column
    private String imageURL;

    @OneToMany(targetEntity = Food.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> availableFoods;

    @OneToMany(targetEntity = Order.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;

    public FoodCompany() {
        super();
    }

    public List<Food> getAvailableFoods() {
        return availableFoods;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setAvailableFoods(List<Food> availableFoods) {
        this.availableFoods = availableFoods;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Username: " + getUsername() + "\n" +
                "Password: " + getPassword() + "\n" +
                "Phone: " + getPhoneNumber() + "\n" +
                "Business code: " + getBusinessCode() + "\n" +
                "Email: " + getEmail() + "\n" +
                "imageUrl: " + getImageURL() + "\n" +
                "country: " + getAddress().getCountry() + "\n" +
                "city: " + getAddress().getCity() + "\n" +
                "district: " + getAddress().getDistrict() + "\n" +
                "street: " + getAddress().getStreet() + "\n" +
                "building: " + getAddress().getBuilding();

    }
}
