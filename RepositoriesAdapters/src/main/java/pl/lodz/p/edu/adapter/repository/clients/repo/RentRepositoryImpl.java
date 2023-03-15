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
    public RentEnt get(UUID entityId) {
        return em.createNamedQuery(RentEnt.FIND_BY_ID, RentEnt.class)
                .setParameter("id", entityId)
                .getSingleResult();
    }

    @Override
    public List<RentEnt> getAll() {
        return em.createNamedQuery(RentEnt.FIND_ALL, RentEnt.class)
                .getResultList();
    }

    @Override
    public void add(RentEnt elem) {
        em.persist(elem);
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
        return em.merge(elem);
    }

    @Override
    public List<RentEnt> getRentsByClient(ClientEnt client) {
        return em.createNamedQuery(RentEnt.FIND_BY_CLIENT, RentEnt.class)
                .setParameter("client", client)
                .getResultList();
    }

    @Override
    public List<RentEnt> getRentsByEquipment(EquipmentEnt equipment) {
        return em.createNamedQuery(RentEnt.FIND_BY_EQUIPMENT, RentEnt.class)
                .setParameter("equipment", equipment)
                .getResultList();
    }


}
