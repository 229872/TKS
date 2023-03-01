package pl.lodz.p.edu.rest.service.impl;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import pl.lodz.p.edu.rest.exception.*;
import pl.lodz.p.edu.rest.DTO.EquipmentDTO;
import pl.lodz.p.edu.rest.model.Equipment;
import pl.lodz.p.edu.rest.service.api.EquipmentService;
import pl.lodz.p.edu.rest.repository.api.EquipmentRepository;

import java.util.List;
import java.util.UUID;

@Transactional
public class EquipmentServiceImpl implements EquipmentService {

    @Inject
    private EquipmentRepository equipmentRepository;

    protected EquipmentServiceImpl() {
    }

    public void add(Equipment equipment) {
        equipmentRepository.add(equipment);
    }

    public Equipment get(UUID uuid) {
        return equipmentRepository.get(uuid);
    }

    public List<Equipment> getAll() {
        return equipmentRepository.getAll();
    }

    public List<Equipment> getAvailable() {
        List<Equipment> allEquipment = equipmentRepository.getAll();
        List<Equipment> availableEquipment ;
        return allEquipment;
    }

    public void update(UUID entityId, EquipmentDTO equipmentDTO) throws IllegalModificationException {
        synchronized (equipmentRepository) {
            Equipment equipment = equipmentRepository.get(entityId);
            equipment.merge(equipmentDTO);
            equipmentRepository.update(equipment);
        }
    }

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
