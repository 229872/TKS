package pl.lodz.p.edu.core.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.exception.ObjectNotFoundServiceException;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;
import pl.lodz.p.edu.core.domain.model.Equipment;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class EquipmentServiceImpl implements EquipmentServicePort {

    private final EquipmentRepositoryPort equipmentRepository;

    @Inject
    public EquipmentServiceImpl(EquipmentRepositoryPort equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment add(Equipment equipment) {
        return equipmentRepository.add(equipment);
    }

    @Override
    public Equipment get(UUID uuid) throws ObjectNotFoundServiceException {
        return equipmentRepository.get(uuid);
    }

    @Override
    public List<Equipment> getAll() {
        return equipmentRepository.getAll();
    }

    @Override
    public Equipment update(UUID uuid, Equipment newEquipmentData) throws IllegalModificationException, ObjectNotFoundServiceException {
        synchronized (equipmentRepository) {
            Equipment equipment = equipmentRepository.get(uuid);
            equipment.update(newEquipmentData);
            return equipmentRepository.update(equipment);
        }
    }

    @Override
    public void remove(UUID uuid) throws ConflictException, ObjectNotFoundServiceException {
        Equipment equipment = equipmentRepository.get(uuid);
        if (!equipmentRepository.isEquipmentRented(uuid)) {
            equipmentRepository.remove(equipment);
        } else {
            throw new ConflictException("There exists unfinished rent for this equipment");
        }
    }
}
