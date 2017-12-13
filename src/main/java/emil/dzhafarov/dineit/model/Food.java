package emil.dzhafarov.dineit.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "food", schema = "dineit")
public class Food implements Serializable {

    private static final long serialVersionUID = -7869952242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToMany(targetEntity = Order.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="order_foods",
            joinColumns=@JoinColumn(name="food_id"),
            inverseJoinColumns=@JoinColumn(name="order_id")
    )
    private List<Order> orders;

    public Food() {}

    public Long getId() {
        return id;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
