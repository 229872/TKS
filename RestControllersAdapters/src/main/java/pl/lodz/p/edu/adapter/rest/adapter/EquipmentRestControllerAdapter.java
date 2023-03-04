package pl.lodz.p.edu.adapter.rest.adapter;

import pl.lodz.p.edu.core.domain.exception.ConflictException;
import pl.lodz.p.edu.core.domain.exception.IllegalModificationException;
import pl.lodz.p.edu.core.domain.model.Equipment;
import pl.lodz.p.edu.ports.incoming.EquipmentServicePort;

import java.util.List;
import java.util.UUID;

public class EquipmentRestControllerAdapter  {
    @Override
    public void add(Equipment equipment) {

    }
    

    @Override
    public List<Equipment> getAll() {
        return null;
    }

    @Override
    public Equipment get(UUID uuid) {
        return null;
    }

    @Override
    public void update(UUID entityId, Equipment equipment) throws IllegalModificationException {

    }

    @Override
    public void remove(UUID uuid) throws ConflictException {

    }
}
