package nl.fontys.kwetter.poc;

import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.implementation.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceUnitTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IKwetterRepository kwetterRepository;

    @Mock
    private ICredentialsRepository credentialsRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        //adminService = new AdminService();
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(credentialsRepository.findAll()).thenReturn(new ArrayList<>());
        when(kwetterRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @DisplayName("Change the role of a user")
    @Test
    void changeTheRoleOfAUser() {
        when(userRepository.findById(0L)).thenReturn(Optional.of(new User(Role.USER)));

        try {
            User user = adminService.changeRole(0L, Role.MODERATOR);

            assertNotNull(user);
            assertEquals(Role.MODERATOR, user.getRole());
        } catch (UserDoesNotExist userDoesNotExist) {
            fail("This exception should not have been thrown");
        }
    }

    @DisplayName("User does not exist while changing the role")
    @Test
    void failToChangeTheRoleOfAUser() {
        when(userRepository.findById(0L)).thenReturn(Optional.empty());

        try {
            adminService.changeRole(0L, Role.MODERATOR);
        } catch (UserDoesNotExist userDoesNotExist) {
            assertEquals("User with the id:0 could not be found.", userDoesNotExist.getMessage());
        }
    }
}
