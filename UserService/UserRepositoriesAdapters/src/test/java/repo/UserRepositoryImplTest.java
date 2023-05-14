package repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.users.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.users.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.users.data.ClientEnt;
import pl.lodz.p.edu.adapter.repository.users.data.UserEnt;
import pl.lodz.p.edu.adapter.repository.users.exception.EntityNotFoundRepositoryException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {

    private static UserEnt user;
    @Inject
    private UserRepository repository;

    @BeforeAll
    public static void init() {
        user = new ClientEnt("lubieplacki123", "plackilubie321",
                "Tommy", "Wiseau",
                new AddressEnt("London", "First", "30"));
    }

    @Test
    @Order(1)
    void shouldAddToRepository() {
        int size = repository.getAll().size();
        repository.add(user);
        assertEquals(size + 1, repository.getAll().size());
    }

    @Test
    @Order(2)
    void shouldFindUser() throws EntityNotFoundRepositoryException {
        assertNotNull(user.getEntityId());
        assertDoesNotThrow(() -> repository.get(user.getEntityId()));
        assertEquals(user, repository.get(user.getEntityId()));
    }

    @Test
    @Order(3)
    void shouldNotFindEquipmentAndThrowException() {
        assertThrows(EntityNotFoundRepositoryException.class,
                () -> repository.get(null));
    }

    @Test
    @Order(4)
    void shouldUpdateUser() throws EntityNotFoundRepositoryException {
        String newName = "Maciek";
        ((ClientEnt) user).setFirstName(newName);
        assertDoesNotThrow(() -> repository.update(user));
        UserEnt updatedUser = repository.get(user.getEntityId());
        assertEquals(newName, ((ClientEnt) updatedUser).getFirstName());
    }

    @Test
    @Order(5)
    void shouldDeleteUser() throws EntityNotFoundRepositoryException {
        int size = repository.getAll().size();

        UserEnt userEnt = repository.get(user.getEntityId());
        repository.remove(userEnt);
        assertEquals(size - 1, repository.getAll().size());
    }


}