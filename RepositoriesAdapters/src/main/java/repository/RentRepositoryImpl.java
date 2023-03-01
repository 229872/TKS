package repository;

import entity.EquipmentEnt;
import entity.RentEnt;
import entity.RentEnt_;
import entity.users.ClientEnt;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentRepositoryImpl {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    public RentRepositoryImpl() {}

    public RentEnt get(UUID entityId) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEnt> cq = cb.createQuery(RentEnt.class);
        Root<RentEnt> rent = cq.from(RentEnt.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(RentEnt_.ENTITY_ID), entityId));

        List<RentEnt> rents = em.createQuery(cq).getResultList();

        if (rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for uniqueId: " + entityId);
        }
        return rents.get(0);
    }

    @Transactional
    public List<RentEnt> getAll() {
        TypedQuery<RentEnt> rentQuery = em.createQuery("Select r from RentEnt r", RentEnt.class);
        return rentQuery.getResultList();
    }

    public List<RentEnt> getEquipmentRents(EquipmentEnt e) {
        if(e.getId() == null) {
            return new ArrayList<RentEnt>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEnt> cq = cb.createQuery(RentEnt.class);
        Root<RentEnt> rent = cq.from(RentEnt.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(RentEnt_.EQUIPMENT), e));
        // jakiś błąd z cascade type

        List<RentEnt> rents = em.createQuery(cq).getResultList();
        return rents;
    }

    @Transactional
    public void add(RentEnt elem) {
        em.merge(elem);
    }

    @Transactional
    public void remove(UUID entityId) {
        RentEnt elem = get(entityId);
        em.remove(elem);
    }

    @Transactional
    public void update(RentEnt elem) {
        em.merge(elem);
    }

    public Long count() {
        Long count = em.createQuery("Select count(rent) from RentEnt rent", Long.class).getSingleResult();
        return count;
    }

    public List<RentEnt> getRentByClient(ClientEnt clientP) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEnt> cq = cb.createQuery(RentEnt.class);
        Root<RentEnt> rent = cq.from(RentEnt.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(RentEnt_.CLIENT), clientP));

        TypedQuery<RentEnt> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<RentEnt> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for Client: " + clientP);
        }
        return rents;
    }

    public List<RentEnt> getRentByEq(EquipmentEnt equipment) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEnt> cq = cb.createQuery(RentEnt.class);
        Root<RentEnt> rent = cq.from(RentEnt.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(RentEnt_.EQUIPMENT), equipment));

        TypedQuery<RentEnt> q = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC);
        List<RentEnt> rents = q.getResultList();

        if(rents.isEmpty()) {
            throw new EntityNotFoundException("Rent not found for equipment: " + equipment);
        }
        return rents;
    }


}
