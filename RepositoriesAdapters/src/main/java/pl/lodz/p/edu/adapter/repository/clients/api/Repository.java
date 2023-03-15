package pl.lodz.p.edu.adapter.repository.clients.api;

import pl.lodz.p.edu.adapter.repository.clients.data.AbstractEntity;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface Repository <T extends AbstractEntity> {

    T get(UUID entityId) throws EntityNotFoundException;
    List<T> getAll();
    void add(T entity);
    void remove(T entity);
    T update(T entity) throws IllegalModificationException;
}
