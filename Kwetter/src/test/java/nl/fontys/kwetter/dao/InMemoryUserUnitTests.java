package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
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
    private UserDaoImp userDao;

    @BeforeEach
    void setUp(){
        userDao = new UserDaoImp();
    }

    @AfterEach
    void tearDown() {
        InMemoryCollection.resetMemory();
    }

    @Test
    @DisplayName("A user can login via a username and password")
    void login() {
        Credentials credentials = new Credentials("1@test.nl", "test");

        User user = userDao.login(credentials);

        assertNotNull(user);
        assertEquals(credentials, user.getCredentials());
    }

    @Test
    @DisplayName("A user can't login via a wrong username")
    void failedLoginWrongUsername() {
        Credentials credentials = new Credentials("wrongEmail@test.nl", "test");

        User user = userDao.login(credentials);

        assertNull(user);
    }


    @Test
    @DisplayName("A user can't login via a wrong password")
    void failedLoginWrongPassword() {
        Credentials credentials = new Credentials("1@test.nl", "wrongPassword");

        User user = userDao.login(credentials);

        assertNull(user);
    }

    @Test
    @DisplayName("Get all users")
    void getAllUsers() {
        List<User> users = userDao.getAllUsers();

        assertNotNull(users);
        assertEquals(10, users.size());
    }

    @Test
    @DisplayName("Create a new user")
    void createNewUser() {
        User user = new User(Role.USER);
        user.setName("createNewUser");
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test", user);

        boolean success = userDao.createNewUser(credentials);
        User loginUser = userDao.login(credentials);

        assertTrue(success);
        assertEquals(user, loginUser);
        assertEquals(11, userDao.getAllUsers().size());
    }

    @Test
    @DisplayName("Fail to create a new user because of an already existing username")
    void failToCreateNewUserAlreadyExistsUsername() {
        User user = new User(Role.USER);
        user.setName("createNewUser");
        Credentials credentials = new Credentials("1@test.nl", "wrongPassword", user);

        boolean success = userDao.createNewUser(credentials);
        User loginUser = userDao.login(credentials);

        assertFalse(success);
        assertNull(loginUser);
        assertEquals(10, userDao.getAllUsers().size());
    }

    @Test
    @DisplayName("Fail to create a new user because the user is null")
    void failToCreateNewUserNullUser() {
        Credentials credentials = new Credentials("UniqueEmail@test.nl", "test");

        boolean success = userDao.createNewUser(credentials);
        User loginUser = userDao.login(credentials);

        assertFalse(success);
        assertNull(loginUser);
        assertEquals(10, userDao.getAllUsers().size());
    }

    @Test
    @DisplayName("Update a user")
    void updateUser() {
        Credentials credentials = new Credentials("1@test.nl", "test");
        User user = userDao.login(credentials);

        user.setBio("Dit is een test bio");

        boolean success = userDao.updateUser(user);

        User updatedUser = userDao.login(credentials);

        assertTrue(success);
        assertEquals(user, updatedUser);
        assertEquals("Dit is een test bio", updatedUser.getBio());
    }

    @Test
    @DisplayName("Delete a user")
    void deleteUser() {
        Credentials credentials = new Credentials("1@test.nl", "test");
        User user = userDao.login(credentials);

        boolean success = userDao.deleteUser(user);

        User deletedUser = userDao.login(credentials);

        assertTrue(success);
        assertNull(deletedUser);
        assertEquals(9, userDao.getAllUsers().size());
    }
}
