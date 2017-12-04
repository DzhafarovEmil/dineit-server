package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Address implements Serializable{

    private static final long serialVersionUID = 5559157732242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column
    private String district;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String building;

    public Address() {}

    public Address(String country, String city, String district, String street, String building) {
        this.country = country;
        this.city = city;
        this.district = district;
        this.street = street;
        this.building = building;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
