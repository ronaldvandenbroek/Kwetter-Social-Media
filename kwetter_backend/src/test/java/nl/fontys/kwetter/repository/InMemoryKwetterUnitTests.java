package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the In Memory Kwetter DAO")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
public class InMemoryKwetterUnitTests {

    @Autowired
    private IKwetterRepository kwetterRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    private User user;
    private Credentials credentials;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();

        credentials = new Credentials("5@test.nl", "test", Role.ROLE_USER);
        user = userRepository.findByCredentials(credentials);

        calendar = Calendar.getInstance();
    }

    @Test
    @DisplayName("Create a kwetter for a user")
    void createKwetter() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());

        Kwetter savedKwetter = kwetterRepository.save(kwetter);

        User updatedUser = userRepository.findByCredentials(credentials);

        assertNotNull(savedKwetter);
        assertEquals(12, kwetterRepository.count());
    }

    @Test
    @DisplayName("Delete a kwetter")
    void deleteKwetter() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());
        kwetterRepository.save(kwetter);

        User updatedUser = userRepository.findByCredentials(credentials);

        kwetterRepository.delete(kwetter);

        assertFalse(updatedUser.getCreatedKwetters().contains(kwetter.getUuid()));
        assertEquals(11, kwetterRepository.count());
        assertEquals(0, kwetterRepository.findAllByOwnerId(kwetter.getOwner().getId()).size());
    }

    @Test
    @DisplayName("Fail to deleteKwetter a kwetter because it does not exist")
    void failToDeleteKwetterNonExistent() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());

        kwetterRepository.delete(kwetter);

        assertEquals(11, kwetterRepository.count());
    }

    @Test
    @DisplayName("Get all kwetters from a user")
    void getAllCreatedKwetters() {
        User user = userRepository.findByName("1Test").get(0);

        List<Kwetter> createdKwetters = kwetterRepository.findAllByOwnerId(user.getId());

        assertNotNull(createdKwetters);
        assertEquals(10, createdKwetters.size());
    }
}
