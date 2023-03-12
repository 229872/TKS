package pl.lodz.p.edu.adapter.repository.clients.adapters;

import jakarta.enterprise.context.RequestScoped;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDataToDomainMapper;
import pl.lodz.p.edu.adapter.repository.clients.adapters.mapper.equipment.EquipmentFromDomainToDataMapper;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class EquipmentRepositoryAdapter implements EquipmentRepositoryPort {

    @Inject
    private EquipmentRepository repository;

    @Inject
    private EquipmentFromDataToDomainMapper toDomainMapper;

    @Inject
    private EquipmentFromDomainToDataMapper toDataMapper;

    @Override
    public boolean isEquipmentRented(UUID uuid) {
        return repository.isEquipmentRented(uuid);
    }

    @Override
    public Equipment get(UUID entityId) {
        EquipmentEnt equipmentEnt = repository.get(entityId);
        return convertToDomainModel(equipmentEnt);
    }

    private Equipment convertToDomainModel(EquipmentEnt equipmentEnt) {
        return toDomainMapper.convertToDomainModel(equipmentEnt);
    }

    private EquipmentEnt convertToDataModel(Equipment elem) {
        return toDataMapper.convertToDataModel(elem);
    }

    @Override
    public List<Equipment> getAll() {
        return repository.getAll().stream()
                .map(this::convertToDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Equipment elem) {
        repository.add(convertToDataModel(elem));
    }

    @Override
    public void remove(UUID entityId) {
        repository.remove(entityId);
    }

    @Override
    public void update(Equipment elem) throws IllegalModificationException {
        repository.update(toDataMapper.convertToDataModel(elem));
    }

    @Override
    public long count() {
        return repository.count();
    }

}
