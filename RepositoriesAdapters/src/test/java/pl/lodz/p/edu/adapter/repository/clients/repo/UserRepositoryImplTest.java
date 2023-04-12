package pl.lodz.p.edu.adapter.repository.clients.repo;

import de.hilling.junit.cdi.CdiTestJunitExtension;
import jakarta.inject.Inject;
import jakarta.transaction.RollbackException;
import jakarta.transaction.TransactionalException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.lodz.p.edu.adapter.repository.clients.api.UserRepository;
import pl.lodz.p.edu.adapter.repository.clients.data.AddressEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.AdminEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.ClientEnt;
import pl.lodz.p.edu.adapter.repository.clients.data.users.UserEnt;
import pl.lodz.p.edu.adapter.repository.clients.exception.EntityNotFoundRepositoryException;
import pl.lodz.p.edu.core.domain.model.Address;
import pl.lodz.p.edu.core.domain.model.users.Client;
import pl.lodz.p.edu.core.domain.model.users.User;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(CdiTestJunitExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryImplTest {

    @Inject
    private UserRepository repository;

    private static AddressEnt address;
    private static UserEnt user;

    @BeforeAll
    public static void init() {
        address = new AddressEnt("London", "Firststreet", "30");
        user = new ClientEnt("lubieplacki123", "plackilubie321",
                "Tommy", "Wiseau", address);
    }

    @Test
    @Order(1)
    void shouldAddToRepository() {
        assertEquals(0, repository.getAll().size());
        repository.add(user);
        assertEquals(1, repository.getAll().size());
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
        ((ClientEnt) user).setFirstName(newName);
        assertDoesNotThrow(() -> repository.update(user));
        UserEnt updatedUser = repository.get(user.getEntityId());
        assertEquals(newName, ((ClientEnt) updatedUser).getFirstName());
    }

    @Test
    @Order(5)
    void shouldDeleteUser() throws EntityNotFoundRepositoryException {
        UserEnt userEnt = repository.get(user.getEntityId());
        repository.remove(userEnt);
        assertEquals(0, repository.getAll().size());
    }


}