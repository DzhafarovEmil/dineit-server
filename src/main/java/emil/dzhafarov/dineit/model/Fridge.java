package emil.dzhafarov.dineit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "fridge", schema = "dineit")
@PrimaryKeyJoinColumn(referencedColumnName = "user_id")
public class Fridge extends User {

    private static final long serialVersionUID = -666657732242241606L;

    @Column(name = "start_date", nullable = false)
    private transient LocalDate startDate;

    @Column(name = "floor", nullable = false)
    private Integer floor;

    @Column(name = "capacity", nullable = false)
    private Double capacity;

    public Fridge() {
        super();
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
