package pl.lodz.p.edu.core.service;

import jakarta.inject.Inject;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.core.domain.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotValidException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.ports.incoming.RentServicePort;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;
import pl.lodz.p.edu.ports.outcoming.RentRepositoryPort;
import pl.lodz.p.edu.ports.outcoming.UserRepositoryPort;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Transactional
public class RentServiceImpl implements RentServicePort {


    @Inject
    private RentRepositoryPort rentRepository;

    @Inject
    private UserRepositoryPort userRepository;

    @Inject
    private EquipmentRepositoryPort equipmentRepository;

    protected RentServiceImpl() {}

    @Override
    public List<Rent> getRentByEq(Equipment equipment) {
        try {
            return rentRepository.getRentByEq(equipment);
        } catch(EntityNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Rent> getRentsByClient(Client client) {
        try {
            return rentRepository.getRentByClient(client);
        } catch(EntityNotFoundException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Rent get(UUID uuid) {
        return rentRepository.get(uuid);
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.getAll();
    }

    @Override
    public Rent add(Rent rent) throws ObjectNotValidException, BusinessLogicInterruptException {
        Client client;
        Equipment equipment;
        LocalDateTime now = LocalDateTime.now();

        valideTime(rent, now);

        synchronized (userRepository) {
            try {
                client = (Client) userRepository.getOfType("Client", rent.getEntityId());
            } catch(NoResultException e) {
                throw new ObjectNotValidException("Client of given uuid does not exist");
            }
            synchronized (equipmentRepository) {
                try {
                    equipment = equipmentRepository.get(rent.getEntityId());
                } catch(EntityNotFoundException e) {
                    throw new ObjectNotValidException("Equipment of given uuid does not exist");
                }

                if(checkEquipmentAvailable(equipment, rent.getBeginTime())) {

                    rentRepository.add(rent);
                    return rent;
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
            }
        }
    }


    @Override
    public Rent update(UUID entityId, Rent rent) throws ObjectNotValidException,
            BusinessLogicInterruptException {
        Rent rentDB;
        try {
            rentDB = rentRepository.get(entityId);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Rent not found");
        }

        Client clientDB;
        Equipment equipmentDB;
        LocalDateTime now;
        now = LocalDateTime.now();

        valideTime(rent, now);

        synchronized (userRepository) {
            try {
                clientDB = (Client) userRepository.getOfType("Client", rent.getClient().getEntityId());
            } catch(NoResultException e) {
                throw new ObjectNotValidException("Client of given uuid does not exist");
            }
            synchronized (equipmentRepository) {
                try {
                    equipmentDB = equipmentRepository.get(rent.getEquipment().getEntityId());
                } catch(EntityNotFoundException e) {
                    throw new ObjectNotValidException("Equipment of given uuid does not exist");
                }
                boolean available = this.checkEquipmentAvailable(equipmentDB, rent.getBeginTime());
                if(available) {
                    rentDB.merge(rent, equipmentDB, clientDB);
                    rentRepository.update(rent);
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
                return rent;
            }
        }
    }

    private static void valideTime(Rent rent, LocalDateTime now) throws ObjectNotValidException {
        if (rent.getEndTime() != null && rent.getBeginTime().isAfter(rent.getEndTime())) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
        if(rent.getBeginTime().isBefore(now)) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
    }

    @Override
    public void remove(UUID uuid) throws BusinessLogicInterruptException {
        Rent rent;
        try {
            rent = rentRepository.get(uuid);
        } catch(EntityNotFoundException ignored) {
            return;
        }
        if(rent.getEndTime() == null) {
            rentRepository.remove(uuid);
        } else {
            throw new BusinessLogicInterruptException("Cannot delete ended rent");
        }
    }

    public LocalDateTime whenAvailable(Equipment equipment) {
        LocalDateTime when = LocalDateTime.now();
        List<Rent> equipmentRents = getRentByEq(equipment);
//        List<Rent> equipmentRents = equipment.getEquipmentRents();

        for (Rent rent :
                equipmentRents) {
            if (when.isAfter(rent.getBeginTime()) && when.isBefore(rent.getEndTime())) {
                when = rent.getEndTime();
            }
        }
        return when;
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