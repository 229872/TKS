package pl.lodz.p.edu.adapter.repository.clients.repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.EquipmentRepository;
import pl.lodz.p.edu.adapter.repository.clients.api.RentRepository;
import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.EquipmentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.RentEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RentRepositoryImplTest {

    private static UserEnt user;
    private static EquipmentEnt equipment;

    private static RentEnt rent;

    @Inject
    private RentRepository rentRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private EquipmentRepository equipmentRepository;

    static LocalDateTime now = LocalDateTime.now();
    static LocalDateTime nowPlusYear = now.plusYears(1);
    static LocalDateTime nowPlusTwoYears = now.plusYears(2);

    @BeforeAll
    public static void init() {
        user = new ClientEnt("nielubie", "plackilubie321",
                "Tommy", "Wiseau");
        equipment = new EquipmentEnt("chair11", 30.0, 10.0, 15.0, "red chair");
    }

    @Test
    @Order(1)
    void shouldAddToRepository() {
        equipmentRepository.add(equipment);
        userRepository.add(user);
        rent = new RentEnt(equipment, (ClientEnt) user, nowPlusYear, nowPlusTwoYears);

        assertEquals(0, rentRepository.getAll().size());
        rentRepository.add(rent);
        assertEquals(1, rentRepository.getAll().size());
    }

    @Test
    @Order(2)
    void shouldFindRent() throws EntityNotFoundRepositoryException {
        rent = rentRepository.getAll().get(0);
        assertNotNull(rent.getEntityId());
        assertDoesNotThrow(() -> rentRepository.get(rent.getEntityId()));
        assertEquals(rent, rentRepository.get(rent.getEntityId()));
    }

    @Test
    @Order(3)
    void shouldNotFindRentAndThrowException() {
        assertThrows(EntityNotFoundRepositoryException.class,
                () -> rentRepository.get(null));
    }

//    @Test
//    @Order(4)
//    void shouldUpdateRent() throws EntityNotFoundRepositoryException {
////        String newName = "old chair";
////        equipment.setName(newName);
//        rent.setClientEnt(null); //FIXME
//        assertDoesNotThrow(() -> rentRepository.update(rent)); // ????
//        RentEnt updatedEntity = rentRepository.get(rent.getEntityId());
//        Assertions.fail();
////        assertEquals(newName, updatedEntity.getName());
//    }

    @Test
    @Order(5)
    void shouldDeleteRent() throws EntityNotFoundRepositoryException {
        RentEnt rentFromDb = rentRepository.get(rent.getEntityId());
        rentRepository.remove(rentFromDb);
        assertEquals(0, rentRepository.getAll().size());
    }

}
