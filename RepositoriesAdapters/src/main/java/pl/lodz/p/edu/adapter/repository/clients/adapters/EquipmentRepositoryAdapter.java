package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.ApplicationScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class EquipmentRepositoryAdapter implements EquipmentRepositoryPort {

    @Inject
    private EquipmentRepository repository;

    @Inject
    private EquipmentFromDataToDomainMapper toDomainMapper;

    @Inject
    private EquipmentFromDomainToDataMapper toDataMapper;

    @Override
    public Equipment get(UUID objectId) throws ObjectNotFoundServiceException {
        try {
            EquipmentEnt equipmentEnt = repository.get(objectId);
            return convertToDomainModel(equipmentEnt);

        } catch (EntityNotFoundRepositoryException e) {

            throw new ObjectNotFoundServiceException(e.getMessage(), e.getCause());
        }
    }

    private Equipment convertToDomainModel(EquipmentEnt equipmentEnt) {
        return toDomainMapper.convertToDomainModel(equipmentEnt);
    }

    private EquipmentEnt convertToDataModel(Equipment object) {
        return toDataMapper.convertToDataModel(object);
    }

    @Override
    public List<Equipment> getAll() {
        return repository.getAll().stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Equipment object) {
        repository.add(convertToDataModel(object));
    }

    @Override
    public void remove(Equipment object) {
        repository.remove(convertToDataModel(object));
    }

    @Override
    public Equipment update(Equipment object) throws IllegalModificationException {
        EquipmentEnt equipmentEnt = repository.update(toDataMapper.convertToDataModel(object));
        return toDomainMapper.convertToDomainModel(equipmentEnt);
    }

    @Override
    public boolean isEquipmentRented(UUID uuid) {
        return repository.isEquipmentRented(uuid);
    }
}
