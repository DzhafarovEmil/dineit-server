package emil.dzhafarov.dineit.service;

import emil.dzhafarov.dineit.model.QRCode;
import emil.dzhafarov.dineit.persistence.QRCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class QRCodeService implements RestContract<QRCode> {

    @Autowired
    private QRCodeRepository repository;

    public List<QRCode> getAll() {
        List<QRCode> codes = new LinkedList<>();
        repository.findAll().forEach(codes::add);

        return codes;
    }

    public QRCode findById(Long id) {
        return repository.findOne(id);
    }

    public boolean isExist(QRCode qrCode) {
        return repository.exists(qrCode.getId());
    }

    public Long create(QRCode qrCode) {
        return repository.save(qrCode).getId();
    }

    public void update(QRCode qrCode) {
        repository.save(qrCode);
    }

    public void deleteById(Long id) {
        repository.delete(id);
    }

    public QRCode findByData(byte[] data) {
        return repository.findByData(data);
    }
}
