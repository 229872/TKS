package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.AbstractEntity;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface Repository<T extends AbstractEntity> {

    T get(UUID entityId);
    List<T> getAll();
    void add(T elem);
    void remove(UUID entityId);
    void update(T elem) throws IllegalModificationException;
    Long count();
}
