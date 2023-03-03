package clients.adapters;

import clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import clients.adapters.mapper.rent.RentFromDataToDomainMapper;
import clients.adapters.mapper.rent.RentFromDomainToDataMapper;
import clients.adapters.mapper.user.UserFromDomainToDataMapper;
import clients.api.RentRepository;
import clients.data.EquipmentEnt;
import clients.data.RentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.model.Rent;
import pl.lodz.p.edu.model.users.Client;
import pl.lodz.p.edu.outcoming.RentRepositoryPort;
import pl.lodz.p.edu.outcoming.RepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        RentEnt rentEnt = toDataMapper.convertToDataModel(elem);
        repository.add(rentEnt);
    }

    @Override
    public void remove(UUID entityId) {
        repository.remove(entityId);
    }

    @Override
    public void update(Rent elem) {
        RentEnt rentEnt = toDataMapper.convertToDataModel(elem);
        repository.update(rentEnt);
    }

    @Override
    public long count() {
        return repository.count();
    }
}
