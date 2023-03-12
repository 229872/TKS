package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.rent.RentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.user.UserFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.RentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.core.domain.model.Rent;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.ports.outcoming.RentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
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
    public List<Rent> getRentByEq(Equipment equipment) {
        return repository.getRentByEq(equipmentToDataMapper.convertToDataModel(equipment))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> getRentByClient(Client client) {
        return repository.getRentByClient(userToDataMapper.convertClientToDataModel(client))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Rent> getEquipmentRents(Equipment equipment) {
        return repository.getEquipmentRents(equipmentToDataMapper.convertToDataModel(equipment))
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Rent get(UUID entityId) {
        return convertToDomainModel(repository.get(entityId));
    }

    @Override
    public List<Rent> getAll() {
        return repository.getAll()
                .stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Rent elem) {
        RentEnt rentEnt = convertToDataModel(elem);
        repository.add(rentEnt);
    }

    @Override
    public void remove(UUID entityId) {
        repository.remove(entityId);
    }

    @Override
    public void update(Rent elem) {
        RentEnt rentEnt = convertToDataModel(elem);
        repository.update(rentEnt);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
