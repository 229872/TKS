package pl.lodz.p.edu.adapter.repository.clients.repo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;


import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
@NoArgsConstructor
public class EquipmentRepositoryImpl implements EquipmentRepository {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    @Override
    public EquipmentEnt get(UUID entityId) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(EquipmentEnt.FIND_BY_ID, EquipmentEnt.class)
                    .setParameter("id", entityId)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<EquipmentEnt> getAll() {
        return em.createNamedQuery(EquipmentEnt.FIND_ALL, EquipmentEnt.class)
                .getResultList();
    }

    @Override
    public void add(EquipmentEnt elem) {
        em.persist(elem);
    }

    @Override
    public void remove(EquipmentEnt entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    @Override
    public EquipmentEnt update(EquipmentEnt elem) {
        EquipmentEnt temp = em.merge(elem);
        em.lock(temp, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return temp;
    }

    @Override
    public boolean isEquipmentRented(UUID entityId) {
        Query q = em.createQuery("Select count(rent) from RentEnt rent where rent.equipmentEnt.entityId = :id", Long.class);
        q.setParameter("id", entityId);
        Long count = (Long) q.getSingleResult();
        return count != 0;
    }
}