package pl.lodz.p.edu.adapter.repository.clients.repo;

import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt_;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

@RequestScoped
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext(name = "request")
    protected EntityManager em;

    public UserRepositoryImpl() {}

    // get user of any type
    @Transactional
    public UserEnt get(UUID entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEnt> cq = cb.createQuery(UserEnt.class);
        Root<UserEnt> user = cq.from(UserEnt.class);

        cq.select(user);
        cq.where(cb.equal(user.get(UserEnt_.ENTITY_ID), entityId));

        List<UserEnt> users = em.createQuery(cq).getResultList();

        if(users.isEmpty()) {
            throw new EntityNotFoundException("Client not found for uniqueId: " + entityId);
        }
        return users.get(0);
    }

    public UserEnt getOfType(String type, UUID entityId) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.entityId = :entityId and type(user) = " + type, UserEnt.class);
        q.setParameter("entityId", entityId);
        return (UserEnt) q.getSingleResult();
    }

    public UserEnt getByLogin(String type, String login) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.login = :login and type(user) = " + type, UserEnt.class);
        q.setParameter("login", login);
        return (UserEnt) q.getSingleResult();
    }

    public UserEnt getByOnlyLogin(String login) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.login = :login", UserEnt.class);
        q.setParameter("login", login);
        return (UserEnt) q.getSingleResult();
    }

    public UserEnt getByLoginPassword(String login, String password) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.login = :login and user.password = :password", UserEnt.class);
        q.setParameter("login", login);
        q.setParameter("password", password);
        return (UserEnt) q.getSingleResult();
    }

    public List<UserEnt> getAllWithLogin(String type, String login) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.login like :login and type(user) = " + type, UserEnt.class);
        q.setParameter("login", login + "%");
        return q.getResultList();
    }

    public List<UserEnt> getAll()  {
        return em.createQuery("SELECT user FROM UserEnt user", UserEnt.class).getResultList();
    }

    public List<UserEnt> getAllOfType(String type) {
        return em.createQuery("SELECT user FROM UserEnt user WHERE type(user) = " + type, UserEnt.class).getResultList();
    }

    @Transactional
    public void add(UserEnt elem) {
        em.persist(elem);
    }

    @Transactional
    public void remove(UUID entityId) {
        UserEnt elem = get(entityId);
        em.remove(elem);
    }

    @Transactional
    public void update(UserEnt elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.merge(elem);
    }

    @Transactional
    public void updateUuid(UUID entityId, UserEnt elem) {
        Query q = em.createQuery("SELECT user FROM UserEnt user WHERE user.entityId = :entityId", UserEnt.class);
        q.setParameter("entityId", entityId);
        UserEnt existing = (UserEnt) q.getSingleResult();
        existing.merge(elem);
    }

    @Transactional
    public Long count() {
        return em.createQuery("Select count(Client) from ClientEnt client", Long.class)
                .setLockMode(LockModeType.OPTIMISTIC).getSingleResult();
    }
}
