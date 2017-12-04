package emil.dzhafarov.dineit.service;

import java.util.List;

public interface RestContract<T> {

    List<T> getAll();

    T findById(Long id);

    boolean isExist(T obj);

    Long create(T obj);

    void update(T obj);

    void deleteById(Long id);
}
