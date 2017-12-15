package emil.dzhafarov.dineit.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name="\"order\"", schema = "dineit")
public class Order implements Serializable {

    private static final long serialVersionUID = 5009157732289741606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "ordered_time", nullable = false)
    private Long orderedTime;

    @Column(name = "price")
    private Double price;

    @ManyToOne(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Customer customer;

    @ManyToOne(targetEntity = FoodCompany.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private FoodCompany foodCompany;

    @ManyToOne(targetEntity = Fridge.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Fridge fridge;

    @ManyToMany(targetEntity = Food.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Food> foods;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(targetEntity = QRCode.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private QRCode qrCode;

    public Order() {}

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(Long orderedTime) {
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

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
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

    public QRCode getQrCode() {
        return qrCode;
    }

    public void setQrCode(QRCode qrCode) {
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedTime=" + orderedTime +
                ", price=" + price +
                ", customer=" + customer +
                ", foodCompany=" + foodCompany +
                ", fridge=" + fridge +
                ", foods=" + foods +
                ", status=" + status +
                ", qrCode=" + qrCode +
                '}';
    }
}
