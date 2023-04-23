package pl.lodz.p.edu.userports.outgoing;


import pl.lodz.p.edu.user.core.domain.usermodel.exception.IllegalModificationException;
import pl.lodz.p.edu.user.core.domain.usermodel.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.user.core.domain.usermodel.users.AbstractModelData;

import java.util.List;
import java.util.UUID;

public interface RepositoryPort<T extends AbstractModelData> {

    T get(UUID objectId) throws ObjectNotFoundServiceException;
    List<T> getAll();
    T add(T object);
    void remove(T object);
    T update(T object) throws IllegalModificationException;
}
