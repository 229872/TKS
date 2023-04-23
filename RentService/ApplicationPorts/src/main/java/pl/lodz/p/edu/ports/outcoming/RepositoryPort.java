package pl.lodz.p.edu.ports.outcoming;

import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.AbstractModelData;

import java.util.List;
import java.util.UUID;

public interface RepositoryPort <T extends AbstractModelData> {

    T get(UUID objectId) throws ObjectNotFoundServiceException;
    List<T> getAll();
    T add(T object);
    void remove(T object);
    T update(T object) throws IllegalModificationException;
}
