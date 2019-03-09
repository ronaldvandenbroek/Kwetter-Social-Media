package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.CredentialsRepository;
import nl.fontys.kwetter.repository.memory.KwetterRepository;
import nl.fontys.kwetter.repository.memory.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the In Memory Kwetter DAO")
public class InMemoryKwetterUnitTests {

    private KwetterRepository kwetterRepository;
    private CredentialsRepository credentialsRepository;
    private UserRepository userRepository;
    private User user;
    private Credentials credentials;
    private Calendar calendar;

    @BeforeEach
    void setUp() {
        kwetterRepository = new KwetterRepository();
        credentialsRepository = new CredentialsRepository();
        userRepository = new UserRepository();

        credentials = new Credentials("5@test.nl", "test");
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
        assertEquals(11, kwetterRepository.count());
    }

    @Test
    @DisplayName("Delete a kwetter")
    void deleteKwetter() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());
        kwetterRepository.save(kwetter);

        User updatedUser = userRepository.findByCredentials(credentials);

        kwetterRepository.delete(kwetter);


        assertFalse(updatedUser.getCreatedKwetters().contains(kwetter));
        assertEquals(10, kwetterRepository.count());
        assertEquals(0, kwetterRepository.findAllByOwnerId(kwetter.getOwner().getId()).size());
    }

    @Test
    @DisplayName("Fail to delete a kwetter because it does not exist")
    void failToDeleteKwetterNonExistent() {
        Kwetter kwetter = new Kwetter("NewKwetter", user, calendar.getTime());

        kwetterRepository.delete(kwetter);

        assertEquals(10, kwetterRepository.count());
    }

    @Test
    @DisplayName("Get all kwetters from a user")
    void getAllCreatedKwetters() {
        User user = new User(Role.USER, 0L);

        assertEquals(0, user.getCreatedKwetters().size());

        List<Kwetter> createdKwetters = kwetterRepository.findAllByOwnerId(user.getId());

        assertNotNull(createdKwetters);
        assertEquals(10, createdKwetters.size());
    }
}
