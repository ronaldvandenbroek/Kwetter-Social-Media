package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Testing the Admin Service")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
class AdminServiceIntegrationTest {

    private Credentials testCredentials;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();

        String email = "1@test.nl";
        String password = "test";

        testCredentials = new Credentials(email, password, Role.ROLE_USER);

        Collection<User> users = InMemoryDatabase.userCollection();
    }

    @Test
    @DisplayName("Change the role of a user")
    void changeRole() {
        try {
            Credentials changedCredentials = adminService.changeRole(testCredentials.getEmail(), Role.ROLE_MOD);

            Credentials moderator = adminService.getFullCredentials(testCredentials.getEmail());

            assertEquals(Role.ROLE_MOD, changedCredentials.getRole());
            assertEquals(Role.ROLE_MOD, moderator.getRole());
        } catch (ModelNotFoundException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("Get a list of all users")
    void getAllUsers() {
        List<User> allUsers = adminService.getAllUsers();

        assertEquals(10, allUsers.size());
    }
}