package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Business extends User {

    private static final long serialVersionUID = 47387732242241606L;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(targetEntity = Address.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Address address;

    @Column(unique = true, nullable = false)
    private Long businessCode;

    @Column
    private String ownerName;

    @Column
    private String email;

    @Column
    private transient LocalDate registrationDate;

    @ElementCollection
    private Map<String, String> socialNetworkRefs;

    public Business() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Long getBusinessCode() {
        return businessCode;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Map<String, String> getSocialNetworkRefs() {
        return socialNetworkRefs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setBusinessCode(Long businessCode) {
        this.businessCode = businessCode;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setSocialNetworkRefs(Map<String, String> socialNetworkRefs) {
        this.socialNetworkRefs = socialNetworkRefs;
    }
}
