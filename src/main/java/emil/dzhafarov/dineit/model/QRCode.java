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
    private byte[] data;

    public QRCode() {
        super();
    }

    public QRCode(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
