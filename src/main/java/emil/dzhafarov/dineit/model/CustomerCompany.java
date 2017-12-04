package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "customer_company", schema = "dineit")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class CustomerCompany extends Business {

    private static final long serialVersionUID = 2229157732242241606L;

    @OneToMany(targetEntity = Customer.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Customer> localCustomers;

    @OneToMany(targetEntity = Fridge.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Fridge> localFridges;

    public CustomerCompany() {
        super();
    }

    public Set<Customer> getLocalCustomers() {
        return localCustomers;
    }

    public Set<Fridge> getLocalFridges() {
        return localFridges;
    }

    public void setLocalCustomers(Set<Customer> localCustomers) {
        this.localCustomers = localCustomers;
    }

    public void setLocalFridges(Set<Fridge> localFridges) {
        this.localFridges = localFridges;
    }
}
