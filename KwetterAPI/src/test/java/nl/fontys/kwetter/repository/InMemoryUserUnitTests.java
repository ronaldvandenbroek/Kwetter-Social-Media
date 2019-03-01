package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.repository.memory.CredentialsRepository;
import nl.fontys.kwetter.repository.memory.UserRepository;
import nl.fontys.kwetter.repository.memory.data.InMemoryData;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the In Memory User DAO")
public class InMemoryUserUnitTests {
    private UserRepository userRepository;
    private CredentialsRepository credentialsRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        credentialsRepository = new CredentialsRepository();
    }

    @AfterEach
    void tearDown() {
        InMemoryData.resetMemory();
    }

    @Test
    @DisplayName("A user can login via a username and password")
    void login() {
        Credentials credentials = new Credentials("1@test.nl", "test");

        User user = userRepository.findByCredentials(credentials);

        assertNotNull(user);
        assertEquals(credentials, user.getCredentials());
    }

    @Test
    @DisplayName("A user can't login via a wrong username")
    void failedLoginWrongUsername() {
        Credentials credentials = new Credentials("wrongEmail@test.nl", "test");

        User user = userRepository.findByCredentials(credentials);

        assertNull(user);
    }


    @Test
    @DisplayName("A user can't login via a wrong password")
    void failedLoginWrongPassword() {
        Credentials credentials = new Credentials("1@test.nl", "wrongPassword");

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
        User user = new User(Role.USER);
        user.setName("createNewUser");
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test", user);

        credentialsRepository.save(credentials);
        User loginUser = userRepository.findByCredentials(credentials);

        assertEquals(user, loginUser);
        assertEquals(11, userRepository.count());
    }

    @Test
    @DisplayName("Fail to create a new user because of an already existing username")
    void failToCreateNewUserAlreadyExistsUsername() {
        User user = new User(Role.USER);
        user.setName("createNewUser");
        Credentials credentials = new Credentials("1@test.nl", "wrongPassword", user);

        credentialsRepository.save(credentials);
        User loginUser = userRepository.findByCredentials(credentials);

        assertNull(loginUser);
        assertEquals(10, userRepository.count());
    }

    @Test
    @DisplayName("Fail to create a new user because the user is null")
    void failToCreateNewUserNullUser() {
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test");

        credentialsRepository.save(credentials);
        User loginUser = userRepository.findByCredentials(credentials);

        assertNull(loginUser);
        assertEquals(10, userRepository.count());
    }

    @Test
    @DisplayName("Update a user")
    void updateUser() {
        Credentials credentials = new Credentials("1@test.nl", "test");
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
        Credentials credentials = new Credentials("1@test.nl", "test");
        User user = userRepository.findByCredentials(credentials);

        userRepository.delete(user);

        User deletedUser = userRepository.findByCredentials(credentials);

        assertNull(deletedUser);
        assertEquals(9, userRepository.count());
    }
}
