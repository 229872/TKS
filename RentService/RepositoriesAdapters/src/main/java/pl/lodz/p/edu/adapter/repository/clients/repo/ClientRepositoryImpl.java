package pl.lodz.p.edu.adapter.repository.clients.repo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.adapter.repository.clients.api.ClientRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
@NoArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    @PersistenceContext(name = "app")
    private EntityManager em;

    @Override
    public ClientEnt get(UUID entityId) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(ClientEnt.FIND_BY_ID, ClientEnt.class)
                    .setParameter("id", entityId)
                    .getSingleResult();

        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public ClientEnt getByLogin(String login) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(ClientEnt.FIND_BY_LOGIN, ClientEnt.class)
                    .setParameter("login", login)
                    .getSingleResult();

        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<ClientEnt> getAll()  {
        return em.createNamedQuery(ClientEnt.FIND_ALL, ClientEnt.class)
                .getResultList();
    }


    @Override
    public ClientEnt add(ClientEnt elem) {
        em.persist(elem);
        return elem;
    }

    @Override
    public void remove(ClientEnt entity) {
       em.remove(em.merge(entity));
    }

    @Override
    public ClientEnt update(ClientEnt elem) {
        em.lock(em.merge(elem), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return em.merge(elem);
    }
}
