package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IAdminService;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ejb.Stateless;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Testing the admin services")
class AdminServiceIntegrationTest {

    private User testUser;

    private IAdminService adminService;
    private IProfileService profileService;
    private ILoginService loginService;

    @BeforeEach
    void setUp() {
        EJBContainer container = EJBContainer.createEJBContainer();
        try {
            adminService = (AdminService) container.getContext().lookup("java:global/classes/AdminService");
            profileService = (ProfileService) container.getContext().lookup("java:global/classes/ProfileService");
            loginService = (LoginService) container.getContext().lookup("java:global/classes/LoginService");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        String email = "0@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(email, password);
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