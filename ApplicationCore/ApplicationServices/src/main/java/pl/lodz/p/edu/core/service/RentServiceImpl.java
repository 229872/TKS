package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.ports.incoming.RentServicePort;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;
import pl.lodz.p.edu.ports.outcoming.RentRepositoryPort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@ApplicationScoped
public class RentServiceImpl implements RentServicePort {

    private final RentRepositoryPort rentRepository;
    private final UserRepositoryPort userRepository;
    private final EquipmentRepositoryPort equipmentRepository;

    @Inject
    public RentServiceImpl(RentRepositoryPort rentRepository, UserRepositoryPort userRepository,
                           EquipmentRepositoryPort equipmentRepository) {
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public List<Rent> getRentsByEquipment(Equipment equipment) {
        return rentRepository.getRentsByEquipment(equipment);
    }

    @Override
    public List<Rent> getRentsByClient(Client client) {
        return rentRepository.getRentsByClient(client);
    }

    @Override
    public Rent get(UUID uuid) throws ObjectNotFoundServiceException {
        return rentRepository.get(uuid);
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.getAll();
    }

    @Override
    public void add(Rent rent) throws BusinessLogicInterruptException,
            ObjectNotFoundServiceException, ObjectNotValidException {

        LocalDateTime now = LocalDateTime.now();
        rent.validateTime(now);

        synchronized (userRepository) { //FIXME ?????????????? WHY IS IT LIKE THIS
            Client client = (Client) userRepository.get(rent.getClient().getEntityId());
            synchronized (equipmentRepository) {
                Equipment equipment = equipmentRepository.get(rent.getEquipment().getEntityId());

                if(checkEquipmentAvailable(equipment, rent.getBeginTime())) {
                    //Must not be detached entity
                    Rent rentDB = new Rent(rent.getBeginTime(), rent.getEndTime(), equipment, client);
                    rentRepository.add(rentDB);
//                    return rent;
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
            }
        }
    }

    @Override
    public Rent update(UUID uuid, Rent rent) throws ObjectNotValidException,
            BusinessLogicInterruptException, ObjectNotFoundServiceException {

        Rent rentDB = rentRepository.get(uuid);
        LocalDateTime now = LocalDateTime.now();
        rent.validateTime(now);

        synchronized (userRepository) {
            Client clientDB = (Client) userRepository.get(rent.getEntityId());
            synchronized (equipmentRepository) {
                Equipment equipmentDB = equipmentRepository.get(rent.getEquipment().getEntityId());
                boolean available = this.checkEquipmentAvailable(equipmentDB, rent.getBeginTime());

                if(available) {
                    rentDB.update(rent, equipmentDB, clientDB);
                    return rentRepository.update(rent);
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
            }
        }
    }



    @Override
    public void remove(UUID uuid) throws BusinessLogicInterruptException, ObjectNotFoundServiceException {
        Rent rent = rentRepository.get(uuid);
        if(rent.getEndTime() == null) {
            rentRepository.remove(rent);
        } else {
            throw new BusinessLogicInterruptException("Cannot delete ended rent");
        }
    }

    public LocalDateTime whenAvailable(Equipment equipment) {
        LocalDateTime now = LocalDateTime.now();
        List<Rent> equipmentRents = getRentsByEquipment(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent : equipmentRents) {
            if (now.isAfter(rent.getBeginTime()) && now.isBefore(rent.getEndTime())) {
                now = rent.getEndTime();
            }
        }
        return now;
    }

    @Override
    public boolean checkEquipmentAvailable(Equipment equipment, LocalDateTime beginTime) {
        List<Rent> rentEquipmentList;
        try {
            rentEquipmentList = rentRepository.getEquipmentRents(equipment);

        } catch(NoResultException e) {
            return true;
        }
        for (int i = 0; i < rentEquipmentList.size(); i++) {
            Rent curRent = rentEquipmentList.get(i);
            if(curRent.getEndTime() == null) {
                if(!curRent.getBeginTime().isEqual(beginTime)) {
                    return false;
                } else {
                    continue;
                }
            }
            if (beginTime.isBefore(curRent.getEndTime())) {
                return false;
            }
        }
        return true;
    }
}