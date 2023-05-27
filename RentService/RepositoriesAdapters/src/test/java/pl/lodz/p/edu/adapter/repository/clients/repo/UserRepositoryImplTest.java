package pl.lodz.p.edu.adapter.repository.clients.repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.ClientRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {

    private static ClientEnt user;
    @Inject
    private ClientRepository repository;

    @BeforeAll
    public static void init() {
        user = new ClientEnt("lubieplacki123", "plackilubie321");
    }

    @Test
    @Order(1)
    void shouldAddToRepository() {
        int size = repository.getAll().size();
        repository.add(user);
        assertEquals(size + 1, repository.getAll().size());
    }
// Whaaaat ??????
//    @Test
//    @Order(2)
//    void shouldNotAddSecondUserWithSameLoginShouldThrowException() {
//        AdminEnt newUser = new AdminEnt(user.getLogin(), "password", "lemon");
//        assertEquals(user.getLogin(), newUser.getLogin());
//        repository.add(newUser);
//    }

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
        user.setFirstName(newName);
        assertDoesNotThrow(() -> repository.update(user));
        ClientEnt updatedUser = repository.get(user.getEntityId());
        assertEquals(newName, (updatedUser).getFirstName());
    }

    @Test
    @Order(5)
    void shouldDeleteUser() throws EntityNotFoundRepositoryException {
        int size = repository.getAll().size();

        ClientEnt userEnt = repository.get(user.getEntityId());
        repository.remove(userEnt);
        assertEquals(size - 1, repository.getAll().size());
    }


}