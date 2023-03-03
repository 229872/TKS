package pl.lodz.p.edu.core.service;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;
import pl.lodz.p.edu.ports.outcoming.EquipmentRepositoryPort;
import pl.lodz.p.edu.core.domain.model.Equipment;

import java.util.List;
import java.util.UUID;

@Transactional
public class EquipmentServiceImpl implements EquipmentServicePort {

    @Inject
    private EquipmentRepositoryPort equipmentRepository;

    protected EquipmentServiceImpl() {
    }

    @Override
    public void add(Equipment equipment) {
        equipmentRepository.add(equipment);
    }

    @Override
    public Equipment get(UUID uuid) {
        return equipmentRepository.get(uuid);
    }

    @Override
    public List<Equipment> getAll() {
        return equipmentRepository.getAll();
    }

    //FIXME ??????????
    public List<Equipment> getAvailable() {
        List<Equipment> allEquipment = equipmentRepository.getAll();
        List<Equipment> availableEquipment;
        return allEquipment;
    }

    @Override
    public void update(UUID entityId, Equipment elem) throws IllegalModificationException {
        synchronized (equipmentRepository) {
            Equipment equipment = equipmentRepository.get(entityId);
            elem.merge(equipment);
            equipmentRepository.update(equipment);
        }
    }

    @Override
    public void remove(UUID uuid) throws ConflictException {
        try {
            if(!equipmentRepository.isEquipmentRented(uuid)) {
                equipmentRepository.remove(uuid);
            } else {
                throw new ConflictException("There exists unfinished rent for this equipment");
            }
        } catch(EntityNotFoundException ignored) {}
    }
}
