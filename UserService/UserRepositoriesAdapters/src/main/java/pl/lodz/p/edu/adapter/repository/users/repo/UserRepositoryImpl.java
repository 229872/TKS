package pl.lodz.p.edu.adapter.repository.users.repo;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.adapter.repository.users.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.users.data.UserEnt;
import pl.lodz.p.edu.adapter.repository.users.exception.EntityNotFoundRepositoryException;

import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
@NoArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(name = "app2")
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
    public UserEnt add(UserEnt elem) {
        em.persist(elem);
        return elem;
    }

    @Override
    public void remove(UserEnt entity) {
       em.remove(em.merge(entity));
    }

    @Override
    public UserEnt update(UserEnt elem) {
        em.lock(em.merge(elem), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
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
