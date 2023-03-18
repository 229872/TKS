package pl.lodz.p.edu.adapter.repository.clients.repo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(name = "app")
    private EntityManager em;

    @Override
    public UserEnt get(UUID entityId) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(UserEnt.FIND_BY_ID, UserEnt.class)
                    .setParameter("id", entityId)
                    .getSingleResult();

        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<UserEnt> getAll()  {
        return em.createNamedQuery(UserEnt.FIND_ALL, UserEnt.class)
                .getResultList();
    }


    @Override
    public void add(UserEnt elem) {
        em.persist(elem);
    }

    @Override
    public void remove(UserEnt entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    @Override
    public UserEnt update(UserEnt elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return em.merge(elem);
    }

    @Override
    public UserEnt getByLogin(String login) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(UserEnt.FIND_BY_LOGIN, UserEnt.class)
                    .setParameter("login", login)
                    .getSingleResult();

        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }
}
