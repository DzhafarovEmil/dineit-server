package emil.dzhafarov.dineit.persistence;

import emil.dzhafarov.dineit.model.QRCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QRCodeRepository extends CrudRepository<QRCode, Long> {

    QRCode findQRCodeByData(byte[] data);
}
