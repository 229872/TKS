package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.RentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.ports.outcoming.RentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class RentRepositoryAdapter implements RentRepositoryPort {
    @Inject
    private RentRepository repository;

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
        return repository.getRentsByEquipment(equipmentToDataMapper.convertToDataModel(equipment))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> getRentsByClient(Client client) {
        return repository.getRentsByClient(userToDataMapper.convertClientToDataModel(client))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Rent get(UUID objectId) {
        try {
            return convertToDomainModel(repository.get(objectId));
        } catch (EntityNotFoundException e) {
            //FIXME CUSTOM EXCEPTION
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Rent> getAll() {
        return repository.getAll()
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Rent object) {
        RentEnt rentEnt = convertToDataModel(object);
        repository.add(rentEnt);
    }

    @Override
    public void remove(Rent object) {
        repository.remove(convertToDataModel(object));
    }

    @Override
    public Rent update(Rent object) {
        RentEnt rentEnt = repository.update(convertToDataModel(object));
        return convertToDomainModel(rentEnt);
    }
}
