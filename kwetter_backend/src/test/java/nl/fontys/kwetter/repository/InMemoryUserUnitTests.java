package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the In Memory User DAO")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
public class InMemoryUserUnitTests {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICredentialsRepository credentialsRepository;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();
    }

    @Test
    @DisplayName("A user can jsfLogin via a username and password")
    void login() {
        Credentials credentials = new Credentials("1@test.nl", "test", Role.ROLE_USER);

        User user = userRepository.findByCredentials(credentials);

        assertNotNull(user);
        assertEquals(credentials, user.getCredentials());
    }

    @Test
    @DisplayName("A user can't jsfLogin via a wrong username")
    void failedLoginWrongUsername() {
        Credentials credentials = new Credentials("wrongEmail@test.nl", "test", Role.ROLE_USER);

        User user = userRepository.findByCredentials(credentials);

        assertNull(user);
    }


    @Test
    @DisplayName("A user can't jsfLogin via a wrong password")
    void failedLoginWrongPassword() {
        Credentials credentials = new Credentials("1@test.nl", "wrongPassword", Role.ROLE_USER);

        User user = userRepository.findByCredentials(credentials);

        assertNull(user);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        assertNotNull(users);
        assertEquals(10, users.size());
    }

    @Test
    @DisplayName("Create a new user")
    void createNewUser() {
        User user = new User();
        user.setName("createNewUser");
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test", Role.ROLE_USER, user);

        credentialsRepository.save(credentials);
        userRepository.save(user);
        User loginUser = userRepository.findByCredentials(credentials);

        assertEquals(user, loginUser);
        assertEquals(11, userRepository.count());
    }

    @Test
    @DisplayName("Fail to create a new user because the user is null")
    void failToCreateNewUserNullUser() {
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test", Role.ROLE_USER);

        credentialsRepository.save(credentials);
        User loginUser = userRepository.findByCredentials(credentials);

        assertNull(loginUser);
        assertEquals(10, userRepository.count());
    }

    @Test
    @DisplayName("Update a user")
    void updateUser() {
        Credentials credentials = new Credentials("1@test.nl", "test", Role.ROLE_USER);
        User user = userRepository.findByCredentials(credentials);

        user.setBio("Dit is een test bio");

        userRepository.save(user);

        User updatedUser = userRepository.findByCredentials(credentials);

        assertEquals(user, updatedUser);
        assertEquals("Dit is een test bio", updatedUser.getBio());
    }

    @Test
    @DisplayName("Delete a user")
    void deleteUser() {
        Credentials credentials = new Credentials("1@test.nl", "test", Role.ROLE_USER);
        User user = userRepository.findByCredentials(credentials);

        userRepository.delete(user);
        credentialsRepository.delete(credentials);

        User deletedUser = userRepository.findByCredentials(credentials);

        assertNull(deletedUser);
        assertEquals(9, userRepository.count());
    }
}
