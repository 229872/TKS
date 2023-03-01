package pl.lodz.p.edu.rest.managers.impl;

import jakarta.inject.Inject;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import pl.lodz.p.edu.rest.exception.BusinessLogicInterruptException;
import pl.lodz.p.edu.rest.exception.ObjectNotValidException;
import pl.lodz.p.edu.rest.data.model.DTO.RentDTO;
import pl.lodz.p.edu.rest.data.model.Equipment;
import pl.lodz.p.edu.rest.data.model.Rent;

import pl.lodz.p.edu.rest.data.model.users.Client;
import pl.lodz.p.edu.rest.managers.api.RentManager;
import pl.lodz.p.edu.rest.repository.api.EquipmentRepository;
import pl.lodz.p.edu.rest.repository.api.RentRepository;
import pl.lodz.p.edu.rest.repository.api.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@Transactional
public class RentManagerImpl implements RentManager {

    Logger logger = Logger.getLogger(RentManagerImpl.class.getName());

    @Inject
    private RentRepository rentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private EquipmentRepository equipmentRepository;

    protected RentManagerImpl() {}

    public List<Rent> getRentByEq(Equipment equipment) {
        try {
            return rentRepository.getRentByEq(equipment);
        } catch(EntityNotFoundException e) {
            return new ArrayList<>();
        }
    }

    public List<Rent> getRentsByClient(Client client) {
        try {
            return rentRepository.getRentByClient(client);
        } catch(EntityNotFoundException e) {
            return new ArrayList<>();
        }
    }


    public Rent get(UUID uuid) {
        return rentRepository.get(uuid);
    }

    public List<Rent> getAll() {
        return rentRepository.getAll();
    }

    public Rent add(RentDTO rentDTO) throws ObjectNotValidException, BusinessLogicInterruptException {
        Client client;
        Equipment equipment;
        LocalDateTime beginTime, endTime = null, now;
        now = LocalDateTime.now();
        try {
            beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
            if(rentDTO.getEndTime() != null) {
                endTime = LocalDateTime.parse(rentDTO.getEndTime());
                if(beginTime.isAfter(endTime)) {
                    throw new ObjectNotValidException("Given dates were invalid");
                }
            }
        } catch(DateTimeParseException e) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
        if(beginTime.isBefore(now)) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
//        logger.info("");
        synchronized (userRepository) {
            try {
                logger.info("LOGGGER: " + rentDTO.getClientUUIDFromString().toString());
                client = (Client) userRepository.getOfType("Client", rentDTO.getClientUUIDFromString());
            } catch(NoResultException e) {
                throw new ObjectNotValidException("Client of given uuid does not exist");
            }
            synchronized (equipmentRepository) {
                try {
                    equipment = equipmentRepository.get(rentDTO.getEquipmentUUIDFromString());
                } catch(EntityNotFoundException e) {
                    throw new ObjectNotValidException("Equipment of given uuid does not exist");
                }
                boolean available = this.checkEquipmentAvailable(equipment, beginTime);
                if(available) {
                    Rent rent = new Rent(beginTime, endTime, equipment, client);
                    rentRepository.add(rent);
                    return rent;
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
            }
        }
    }



    public Rent update(UUID entityId, RentDTO rentDTO) throws ObjectNotValidException,
            BusinessLogicInterruptException {
        Rent rent;
        try {
            rent = rentRepository.get(entityId);
        } catch (NoResultException e) {
            throw new EntityNotFoundException("Rent not found");
        }

        Client client;
        Equipment equipment;
        LocalDateTime now, beginTime, endTime = null;
        now = LocalDateTime.now();

        // date validation
        try {
            beginTime = LocalDateTime.parse(rentDTO.getBeginTime());
            if(rentDTO.getEndTime() != null) {
                endTime = LocalDateTime.parse(rentDTO.getEndTime());
                if(beginTime.isAfter(endTime)) {
                    throw new ObjectNotValidException("Given dates were invalid");
                }
            }
        } catch(DateTimeParseException e) {
            throw new ObjectNotValidException("Given dates were invalid");
        }
        if(beginTime.isBefore(now)) {
            throw new ObjectNotValidException("Given dates were invalid");
        }

        synchronized (userRepository) {
            try {
                client = (Client) userRepository.getOfType("Client", rentDTO.getClientUUIDFromString());
            } catch(NoResultException e) {
                throw new ObjectNotValidException("Client of given uuid does not exist");
            }
            synchronized (equipmentRepository) {
                try {
                    equipment = equipmentRepository.get(rentDTO.getEquipmentUUIDFromString());
                } catch(EntityNotFoundException e) {
                    throw new ObjectNotValidException("Equipment of given uuid does not exist");
                }
                boolean available = this.checkEquipmentAvailable(equipment, beginTime);
                logger.info("available:" + Boolean.toString(available));
                if(available) {
                    rent.merge(rentDTO, equipment, client);
                    rentRepository.update(rent);
                } else {
                    throw new BusinessLogicInterruptException("Equipment not available");
                }
                return rent;
            }
        }
    }

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

    public boolean checkEquipmentAvailable(Equipment equipment, LocalDateTime beginTime) {
        List<Rent> rentEquipmentList;
        try {
            rentEquipmentList = rentRepository.getEquipmentRents(equipment);
            logger.info("equipment rent size: " + rentEquipmentList.size());
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