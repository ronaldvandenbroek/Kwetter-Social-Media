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
        User user = new User(Role.USER);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        try {
            User changedRoleUser = adminService.changeRole(user.getId(), Role.MODERATOR);

            assertNotNull(changedRoleUser);
            assertEquals(Role.MODERATOR, changedRoleUser.getRole());
        } catch (UserDoesNotExist userDoesNotExist) {
            fail("This exception should not have been thrown");
        }
    }

    @DisplayName("User does not exist while changing the role")
    @Test
    void failToChangeTheRoleOfAUser() {
        User user = new User(Role.USER);

        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());


        assertThrows(UserDoesNotExist.class, () -> adminService.changeRole(user.getId(), Role.MODERATOR));
    }
}

