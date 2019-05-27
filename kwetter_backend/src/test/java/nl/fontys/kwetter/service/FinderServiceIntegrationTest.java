package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the Hateoas Service")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
class FinderServiceIntegrationTest {

    private Credentials credentials;
    private Kwetter kwetter;
    private User user;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @Autowired
    private IFinderService finderService;

    @Autowired
    private IAdminService adminService;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();

        // Get one of each type to test
        kwetter = adminService.getAllKwetters().get(0);
        credentials = adminService.getAllCredentials().get(0);
        user = adminService.getAllUsers().get(0);
    }

    @Test
    void getUserById() {
        User foundUser = finderService.getUserById(user.getId());
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void getKwetterById() {
        Kwetter foundKwetter = finderService.getKwetterById(kwetter.getUuid());
        assertNotNull(foundKwetter);
        assertEquals(kwetter.getUuid(), foundKwetter.getUuid());
    }

    @Test
    void getCredentialsById() {
        Credentials foundCredentials = finderService.getCredentialsById(credentials.getEmail());
        assertNotNull(foundCredentials);
        assertEquals(credentials.getEmail(), foundCredentials.getEmail());
    }

    @Test
    void getUserByIdWrongId() {
        assertThrows(ModelNotFoundException.class, () -> finderService.getUserById(null));
    }

    @Test
    void getKwetterByIdWrongId() {
        assertThrows(ModelNotFoundException.class, () -> finderService.getKwetterById(null));
    }

    @Test
    void getCredentialsByIdWrongId() {
        assertThrows(ModelNotFoundException.class, () -> finderService.getCredentialsById(null));
    }
}
