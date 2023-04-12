package pl.lodz.p.edu.adapter.repository.clients.repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
class EquipmentRepositoryImplTest {

    @Inject
    private EquipmentRepository repository;
    @Test
    void get() {
    }

    @Test
    void getAll() {
        assertEquals(0, repository.getAll().size());
    }

    @Test
    void add() {
    }

    @Test
    void remove() {
    }

    @Test
    void update() {
    }

    @Test
    void isEquipmentRented() {
    }
}