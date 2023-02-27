package pl.lodz.p.edu.rest.repository.impl;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.repository.api.EquipmentRepository;
import pl.lodz.p.edu.rest.repository.api.Repository;
import pl.lodz.p.edu.data.model.Equipment;
import pl.lodz.p.edu.data.model.Equipment_;

import java.util.List;
import java.util.UUID;


public class EquipmentRepositoryImpl implements EquipmentRepository {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    public EquipmentRepositoryImpl() {}

    @Override
    public Equipment get(UUID entityId) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Equipment> cq = cb.createQuery(Equipment.class);
        Root<Equipment> equipment = cq.from(Equipment.class);

        cq.select(equipment);
        cq.where(cb.equal(equipment.get(Equipment_.ENTITY_ID), entityId));

        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Equipment> equipmentList = em.createQuery(cq).setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();


        if(equipmentList.isEmpty()) {
            throw new EntityNotFoundException("Equipment not found for uniqueId: " + entityId);
        }
        return equipmentList.get(0);
    }

    @Override
    public List<Equipment> getAll() {
        EntityTransaction et = em.getTransaction();
        et.begin();
        List<Equipment> equipmentList = em.createQuery("Select eq from Equipment eq", Equipment.class)
                .setLockMode(LockModeType.OPTIMISTIC).getResultList();
        et.commit();
        return equipmentList;
    }

    @Override
    @Transactional
    public void add(Equipment elem) {
        em.persist(elem);
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
    }

    @Override
    @Transactional
    public void remove(UUID entityId) {
        Equipment equipment = get(entityId);
        em.lock(equipment, LockModeType.OPTIMISTIC);
        em.remove(equipment);
    }

    @Override
    @Transactional
    public void update(Equipment elem) {
        em.lock(elem, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        em.merge(elem);
    }

    @Override
    @Transactional
    public Long count() {
        return em.createQuery("Select count(eq) from Equipment eq", Long.class).getSingleResult();
    }

    public boolean isEquipmentRented(UUID entityId) {
        Query q = em.createQuery("Select count(rent) from Rent rent where rent.equipment.entityId = :id", Long.class);
        q.setParameter("id", entityId);
        Long count = (Long) q.getSingleResult();
        return count == 0 ? false : true;
    }
}