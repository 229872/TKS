package pl.lodz.p.edu.outcoming;

import pl.lodz.p.edu.rest.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface Repository<T> {

    T get(UUID entityId);
    List<T> getAll();
    void add(T elem);
    void remove(UUID entityId);
    void update(T elem) throws IllegalModificationException;
    Long count();
}
