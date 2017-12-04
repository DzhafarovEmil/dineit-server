package emil.dzhafarov.dineit.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer", schema = "dineit")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Customer extends User {

    private static final long serialVersionUID = 8889157732242241606L;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(targetEntity = Order.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders;

    public Customer() {
        super();
    }

    public Customer(String username, String password, String phoneNumber,
                    String firstName, String lastName, String email, List<Order> orders) {
        super(username, password, phoneNumber);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.orders = orders;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
