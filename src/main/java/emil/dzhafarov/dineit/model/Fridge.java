package emil.dzhafarov.dineit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Fridge extends User {

    private static final long serialVersionUID = -666657732242241606L;

    @Column(nullable = false)
    private transient LocalDate startDate;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Double capacity;

    public Fridge() {
        super();
    }

    public Fridge(String username, String password, String phoneNumber,
                  LocalDate startDate, Integer floor, Double capacity) {
        super(username, password, phoneNumber);
        this.startDate = startDate;
        this.floor = floor;
        this.capacity = capacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public Integer getFloor() {
        return floor;
    }

    public Double getCapacity() {
        return capacity;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }
}
