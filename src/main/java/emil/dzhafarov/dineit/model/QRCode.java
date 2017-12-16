package emil.dzhafarov.dineit.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "qrcode", schema = "dineit")
public class QRCode implements Serializable {
    private static final long serialVersionUID = -7869952242241606L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "qrcode_id")
    private Long id;

    @Column(name = "data", nullable = false)
    private String data;

    public QRCode() {
        super();
    }

    public QRCode(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QRCode qrCode = (QRCode) o;

        return data.equals(qrCode.data);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(data);
    }
}
