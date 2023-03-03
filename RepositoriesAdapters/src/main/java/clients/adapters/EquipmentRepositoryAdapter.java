package clients.adapters;

import clients.api.EquipmentRepository;
import clients.data.EquipmentEnt;
import jakarta.inject.Inject;
import pl.lodz.p.edu.exception.IllegalModificationException;
import pl.lodz.p.edu.model.Equipment;
import pl.lodz.p.edu.outcoming.EquipmentRepositoryPort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EquipmentRepositoryAdapter implements EquipmentRepositoryPort {

    @Inject
    private EquipmentRepository repository;


    @Override
    public boolean isEquipmentRented(UUID uuid) {
        return repository.isEquipmentRented(uuid);
    }

    @Override
    public Equipment get(UUID entityId) {
        EquipmentEnt equipmentEnt = repository.get(entityId);
        return convertToDomainModel(equipmentEnt);
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
        repository.update(convertToDataModel(elem));
    }

    @Override
    public Long count() {
        return repository.count();
    }

    private Equipment convertToDomainModel(EquipmentEnt equipmentEnt) {
        return new Equipment(
                equipmentEnt.getFirstDayCost(),
                equipmentEnt.getNextDaysCost(),
                equipmentEnt.getBail(),
                equipmentEnt.getName()
        );
    }

    private EquipmentEnt convertToDataModel(Equipment equipment) {
        return new EquipmentEnt(
                equipment.getFirstDayCost(),
                equipment.getNextDaysCost(),
                equipment.getBail(),
                equipment.getName()
        );
    }
}
