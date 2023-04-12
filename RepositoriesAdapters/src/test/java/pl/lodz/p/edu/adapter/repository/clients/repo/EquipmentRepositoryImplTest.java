package pl.lodz.p.edu.adapter.repository.clients.repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EquipmentRepositoryImplTest {

    @Inject
    private EquipmentRepository equipmentRepository;

    private static EquipmentEnt equipment;

    @BeforeAll
    public static void init() {
        equipment = new EquipmentEnt("chair", 30.0, 10.0, 15.0, "red chair");
    }

    @Test
    @Order(1)
    void shouldAddToRepository() {
        assertEquals(0, equipmentRepository.getAll().size());
        equipmentRepository.add(equipment);
        assertEquals(1, equipmentRepository.getAll().size());
    }

    @Test
    @Order(2)
    void shouldFindEquipment() throws EntityNotFoundRepositoryException {
        List<EquipmentEnt> all = equipmentRepository.getAll();
        EquipmentEnt found = all.get(0);
        assertNotNull(found.getEntityId());
        assertEquals(1, equipmentRepository.getAll().size());
        assertDoesNotThrow(() -> equipmentRepository.get(found.getEntityId()));
    }

    @Test
    @Order(3)
    void shouldNotFindEquipmentAndThrowException() {
        assertThrows(EntityNotFoundRepositoryException.class,
                () -> equipmentRepository.get(null));
    }

    @Test
    @Order(4)
    void shouldUpdateEquipment() throws EntityNotFoundRepositoryException {
        String newName = "old chair";
        equipment.setName(newName);
        assertDoesNotThrow(() -> equipmentRepository.update(equipment));
        EquipmentEnt updatedEntity = equipmentRepository.get(equipment.getEntityId());
        assertEquals(newName, updatedEntity.getName());
    }

    @Test
    @Order(5)
    void shouldDeleteEquipment() throws EntityNotFoundRepositoryException {
        EquipmentEnt equipmentFromDb = equipmentRepository.get(equipment.getEntityId());
        equipmentRepository.remove(equipmentFromDb);
        assertEquals(0, equipmentRepository.getAll().size());
    }
}