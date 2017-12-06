package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="\"order\"", schema = "dineit")
public class Order implements Serializable {

    private static final long serialVersionUID = 5009157732289741606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private transient LocalDateTime orderedTime;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(targetEntity = FoodCompany.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FoodCompany foodCompany;

    @ManyToOne(targetEntity = Fridge.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Fridge fridge;

    @ManyToMany(targetEntity = Food.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Food> foods;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Fridge getFridge() {
        return fridge;
    }

    public void setFridge(Fridge fridge) {
        this.fridge = fridge;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public FoodCompany getFoodCompany() {
        return foodCompany;
    }

    public void setFoodCompany(FoodCompany foodCompany) {
        this.foodCompany = foodCompany;
    }
}
