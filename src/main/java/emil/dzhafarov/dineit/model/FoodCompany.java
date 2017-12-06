package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "food_company", schema = "dineit")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class FoodCompany extends Business {

    private static final long serialVersionUID = 999997732242241606L;

    @Column
    private String imageURL;

    @OneToMany(targetEntity = Food.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> availableFoods;

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

}
