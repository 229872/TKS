package pl.lodz.p.edu.ports.outcoming;

import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface RepositoryPort<T> {

    T get(UUID entityId);
    List<T> getAll();
    void add(T elem);
    void remove(UUID entityId);
    void update(T elem) throws IllegalModificationException;
    long count();
}
