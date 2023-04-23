package pl.lodz.p.edu.adapter.repository.users.api;


import pl.lodz.p.edu.adapter.repository.users.data.AbstractEntity;
import pl.lodz.p.edu.adapter.repository.users.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.adapter.repository.users.exception.IllegalModificationException;

import java.util.List;
import java.util.UUID;

public interface Repository <T extends AbstractEntity> {

    T get(UUID entityId) throws EntityNotFoundRepositoryException;
    List<T> getAll();
    T add(T entity);
    void remove(T entity);
    T update(T entity) throws IllegalModificationException;
}
