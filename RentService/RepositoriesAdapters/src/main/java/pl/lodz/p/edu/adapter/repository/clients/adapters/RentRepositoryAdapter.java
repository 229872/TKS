package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.api.RentRepository;
import pl.lodz.p.edu.adapter.repository.clients.api.ClientRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.ports.outgoing.RentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class RentRepositoryAdapter implements RentRepositoryPort {
    @Inject
    private RentRepository rentRepository;

    @Inject
    private ClientRepository userRepository;

    @Inject
    private EquipmentRepository equipmentRepository;

    @Inject
    private RentFromDomainToDataMapper toDataMapper;

    @Inject
    private RentFromDataToDomainMapper toDomainMapper;

    @Inject
    private EquipmentFromDomainToDataMapper equipmentToDataMapper;

    @Inject
    private UserFromDomainToDataMapper userToDataMapper;


    private Rent convertToDomainModel(RentEnt rentEnt) {
        return toDomainMapper.convertToDomainModel(rentEnt);
    }

    private RentEnt convertToDataModel(Rent elem) {
        return toDataMapper.convertToDataModel(elem);
    }

    @Override
    public List<Rent> getRentsByEquipment(Equipment equipment) {
        return rentRepository.getRentsByEquipment(equipmentToDataMapper.convertToDataModel(equipment))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> getRentsByClient(UUID clientUuid) {
        return rentRepository.getRentsByClient(clientUuid)
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Rent get(UUID objectId) throws ObjectNotFoundServiceException {
        try {
            return convertToDomainModel(rentRepository.get(objectId));
        } catch (EntityNotFoundRepositoryException e) {
            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Rent> getAll() {
        return rentRepository.getAll()
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Rent add(Rent object) {
        return convertToDomainModel(rentRepository.add(convertToDataModel(object)));
    }


    @Override
    public void remove(Rent object) {
        rentRepository.remove(convertToDataModel(object));
    }

    @Override
    public Rent update(Rent object) {
        RentEnt rentEnt = rentRepository.update(convertToDataModel(object));
        return convertToDomainModel(rentEnt);
    }

    @Override
    public List<Rent> getEquipmentRents(Equipment equipment) {
        EquipmentEnt equipmentEnt = equipmentToDataMapper.convertToDataModel(equipment);
        List<RentEnt> rentList = rentRepository.getEquipmentRents(equipmentEnt);
        return rentList.stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }
}
