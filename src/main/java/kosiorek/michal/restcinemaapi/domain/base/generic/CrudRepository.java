package kosiorek.michal.restcinemaapi.domain.base.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    Optional<T> addOrUpdate(T item);
    Optional<T> findById(ID id);
    Optional<T> deleteById(ID id);
    List<T> findAll();
    List<T> findAllById(Iterable<ID> idsCollection);
    List<T> addAll(List<T> items);
}
