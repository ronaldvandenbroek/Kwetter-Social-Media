package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.DataLoaderTestConfiguration;
import nl.fontys.kwetter.configuration.H2TestConfiguration;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Testing the admin services")
@DataJpaTest
@Import({H2TestConfiguration.class, DataLoaderTestConfiguration.class})
@Transactional
class AdminServiceIntegrationTest {

    private User testUser;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private ILoginService loginService;

    @BeforeEach
    void setUp() {
        String email = "1@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(new Credentials(email, password));
        } catch (CannotLoginException | InvalidModelException e) {
            e.printStackTrace();
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("Change the role of a user")
    void changeRole() {
        try {
            adminService.changeRole(testUser.getId(), Role.MODERATOR);

            User moderator = profileService.getFullProfile(testUser.getId());

            assertEquals(Role.MODERATOR, moderator.getRole());
        } catch (UserDoesntExist e) {
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