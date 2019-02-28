package nl.fontys.kwetter.service;

import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.repository.memory.CredentialsRepository;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.UserRepository;
import nl.fontys.kwetter.service.interfaces.IAdminService;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Testing the admin services")
class AdminServiceIntegrationTest {

    private User testUser;

    private IAdminService adminService;
    private IProfileService profileService;

    @BeforeEach
    void setUp() {
        ModelValidator modelValidator = new ModelValidator();
        ICredentialsRepository credentialsRepository = new CredentialsRepository();
        IUserRepository userRepository = new UserRepository();

        adminService = new AdminService(userRepository);
        profileService = new ProfileService(userRepository, modelValidator);
        ILoginService loginService = new LoginService(credentialsRepository, modelValidator);


        String email = "0@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(new Credentials(email, password));
        } catch (CannotLoginException | InvalidModelException e) {
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