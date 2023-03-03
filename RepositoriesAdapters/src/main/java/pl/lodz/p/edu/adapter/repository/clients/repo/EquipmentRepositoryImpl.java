package pl.lodz.p.edu.adapter.repository.clients.repo;

import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import entity.EquipmentEnt_;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;


import java.util.List;
import java.util.UUID;


public class EquipmentRepositoryImpl implements EquipmentRepository {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    public EquipmentRepositoryImpl() {}


    public EquipmentEnt get(UUID entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<EquipmentEnt> cq = cb.createQuery(EquipmentEnt.class);
        Root<EquipmentEnt> equipment = cq.from(EquipmentEnt.class);

        cq.select(equipment);
        cq.where(cb.equal(equipment.get(EquipmentEnt_.ENTITY_ID), entityId));

        EntityTransaction et = em.getTransaction();
        et.begin();
        List<EquipmentEnt> equipmentList = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();


        if(equipmentList.isEmpty()) {
            throw new EntityNotFoundException("Equipment not found for uniqueId: " + entityId);
        }
        return equipmentList.get(0);
    }

    public List<EquipmentEnt> getAll() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        List<EquipmentEnt> equipmentList = em.createQuery("Select eq from EquipmentEnt eq", EquipmentEnt.class)
                .setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();
        return equipmentList;
    }

    @Transactional
    public void add(EquipmentEnt elem) {
        em.persist(elem);
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    @Transactional
    public void remove(UUID entityId) {
        EquipmentEnt equipment = get(entityId);
        em.lock(equipment, LockModeType.OPTIMISTIC);
        em.remove(equipment);
    }

    @Transactional
    public void update(EquipmentEnt elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.merge(elem);
    }

    @Transactional
    public Long count() {
        return em.createQuery("Select count(eq) from EquipmentEnt eq", Long.class).getSingleResult();
    }

    public boolean isEquipmentRented(UUID entityId) {
        Query q = em.createQuery("Select count(rent) from RentEnt rent where rent.equipmentEnt.entityId = :id", Long.class);
        q.setParameter("id", entityId);
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
}