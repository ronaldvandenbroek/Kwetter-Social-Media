package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.dao.memory.KwetterDaoImp;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the In Memory Kwetter DAO")
public class InMemoryKwetterUnitTests {
    private UserDaoImp userDao;
    private KwetterDaoImp kwetterDao;
    private User user;
    private Credentials credentials;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImp();
        kwetterDao = new KwetterDaoImp();

        credentials = new Credentials("5@test.nl", "test");
        user = userDao.login(credentials);

        calendar = Calendar.getInstance();
    }

    @AfterEach
    void tearDown() {
        InMemoryCollection.resetMemory();
    }

    @Test
    @DisplayName("Create a kwetter for a user")
    void createKwetter() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());

        boolean success = kwetterDao.createNewKwetter(kwetter);

        User updatedUser = userDao.login(credentials);

        assertTrue(success);
        assertEquals(11, kwetterDao.getAllKwetters().size());
    }

    @Test
    @DisplayName("Delete a kwetter")
    void deleteKwetter() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());
        kwetterDao.createNewKwetter(kwetter);

        User updatedUser = userDao.login(credentials);

        boolean deleteSuccess = kwetterDao.deleteKwetter(kwetter);

        assertTrue(deleteSuccess);
        assertFalse(updatedUser.getCreatedKwetters().contains(kwetter));
        assertEquals(11, kwetterDao.getAllKwetters().size());
        assertNull(kwetter.getOwner());
    }

    @Test
    @DisplayName("Fail to delete a kwetter because it does not exist")
    void failToDeleteKwetterNonExistent() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());

        boolean deleteSuccess = kwetterDao.deleteKwetter(kwetter);

        assertFalse(deleteSuccess);
        assertEquals(10, kwetterDao.getAllKwetters().size());
    }

    @Test
    @DisplayName("Get all kwetters from a user")
    void getAllCreatedKwetters() {
        User user = new User(Role.USER, 0L);

        assertEquals(0, user.getCreatedKwetters().size());

        List<Kwetter> createdKwetters = kwetterDao.getAllCreatedKwettersFromUser(user);

        assertNotNull(createdKwetters);
        assertEquals(10, createdKwetters.size());
    }
}
