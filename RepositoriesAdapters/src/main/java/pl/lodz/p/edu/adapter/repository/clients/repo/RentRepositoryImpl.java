package pl.lodz.p.edu.adapter.repository.clients.repo;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.NoArgsConstructor;
import pl.lodz.p.edu.adapter.repository.clients.api.RentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt_;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Transactional
@ApplicationScoped
@NoArgsConstructor
public class RentRepositoryImpl implements RentRepository {

    @PersistenceContext(unitName = "app")
    private EntityManager em;

    @Override
    public RentEnt get(UUID entityId) throws EntityNotFoundRepositoryException {
        try {
            return em.createNamedQuery(RentEnt.FIND_BY_ID, RentEnt.class)
                    .setParameter("id", entityId)
                    .getSingleResult();
        } catch (PersistenceException e) {
            throw new EntityNotFoundRepositoryException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<RentEnt> getAll() {
        return em.createNamedQuery(RentEnt.FIND_ALL, RentEnt.class)
                .getResultList();
    }

    @Override
    public void add(RentEnt elem) {

        //FIXME Worst piece of code in an existence
        ClientEnt clientEn = (ClientEnt) em.createNamedQuery(UserEnt.FIND_BY_ID, UserEnt.class)
                .setParameter("id", elem.getClientEnt().getEntityId())
                .getSingleResult();

        EquipmentEnt equipmentEnt = em.createNamedQuery(EquipmentEnt.FIND_BY_ID, EquipmentEnt.class)
                .setParameter("id", elem.getEquipmentEnt().getEntityId())
                .getSingleResult();

        em.persist(new RentEnt(equipmentEnt, clientEn, elem.getBeginTime(), elem.getEndTime()));

    }

    @Override
    public void remove(RentEnt entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    @Override
    public RentEnt update(RentEnt elem) {
        em.lock(em.merge(elem), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        return elem; //fixme i don't like it
    }

    @Override
    public List<RentEnt> getRentsByClient(UUID clientUuid) {
        return em.createNamedQuery(RentEnt.FIND_BY_CLIENT_ID, RentEnt.class)
                .setParameter("clientId", clientUuid)
                .getResultList();
    }

    @Override
    public List<RentEnt> getEquipmentRents(EquipmentEnt equipment) {
        if(equipment.getEntityId() == null) {
            return new ArrayList<RentEnt>();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RentEnt> cq = cb.createQuery(RentEnt.class);
        Root<RentEnt> rent = cq.from(RentEnt.class);

        cq.select(rent);
        cq.where(cb.equal(rent.get(RentEnt_.EQUIPMENT_ENT), equipment));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<RentEnt> getRentsByEquipment(EquipmentEnt equipment) {
        return em.createNamedQuery(RentEnt.FIND_BY_EQUIPMENT_ID, RentEnt.class)
                .setParameter("equipmentId", equipment.getEntityId())
                .getResultList();
    }


}
