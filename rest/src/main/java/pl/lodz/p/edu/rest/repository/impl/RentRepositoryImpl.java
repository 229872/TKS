package pl.lodz.p.edu.rest.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.repository.api.Repository;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.Rent;
import pl.lodz.p.edu.data.model.Rent_;
import pl.lodz.p.edu.data.model.users.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentRepositoryImpl implements Repository<Rent> {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    public RentRepositoryImpl() {}

    @Override
    public Rent get(UUID entityId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.ENTITY_ID), entityId));

        List<Rent> rents = em.createQuery(cq).getResultList();

        if (rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for uniqueId: " + entityId);
        }
        return rents.get(0);
    }

    @Override
    @Transactional
    public List<Rent> getAll() {
        TypedQuery<Rent> rentQuery = em.createQuery("Select r from Rent r", Rent.class);
        return rentQuery.getResultList();
    }

    public List<Rent> getEquipmentRents(Equipment e) {
        if(e.getId() == null) {
            return new ArrayList<Rent>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), e));
        // jakiś błąd z cascade type

        List<Rent> rents = em.createQuery(cq).getResultList();
        return rents;
    }

    @Override
    @Transactional
    public void add(Rent elem) {
        em.merge(elem);
    }

    @Override
    @Transactional
    public void remove(UUID entityId) {
        Rent elem = get(entityId);
        em.remove(elem);
    }

    @Override
    @Transactional
    public void update(Rent elem) {
        em.merge(elem);
    }

    @Override
    public Long count() {
        Long count = em.createQuery("Select count(rent) from Rent rent", Long.class).getSingleResult();
        return count;
    }

    public List<Rent> getRentByClient(Client clientP) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.CLIENT), clientP));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for Client: " + clientP);
        }
        return rents;
    }

    public List<Rent> getRentByEq(Equipment equipment) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Rent> cq = cb.createQuery(Rent.class);
        Root<Rent> rent = cq.from(Rent.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(Rent_.EQUIPMENT), equipment));

        TypedQuery<Rent> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<Rent> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for equipment: " + equipment);
        }
        return rents;
    }


}
