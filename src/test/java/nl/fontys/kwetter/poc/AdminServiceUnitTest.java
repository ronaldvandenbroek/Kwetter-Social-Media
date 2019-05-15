package nl.fontys.kwetter.poc;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Role;
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
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        when(credentialsRepository.findAll()).thenReturn(new ArrayList<>());
        when(kwetterRepository.findAll()).thenReturn(new ArrayList<>());
    }

    @DisplayName("Change the role of a user")
    @Test
    void changeTheRoleOfAUser() {
        Credentials credentials = new Credentials("test@test.nl", "test", Role.ROLE_USER);

        when(credentialsRepository.findById(credentials.getEmail())).thenReturn(Optional.of(credentials));

        try {
            Credentials changedRoleCredentials = adminService.changeRole(credentials.getEmail(), Role.ROLE_MOD);

            assertNotNull(changedRoleCredentials);
            assertEquals(Role.ROLE_MOD, changedRoleCredentials.getRole());
        } catch (ModelNotFoundException e) {
            fail("This exception should not have been thrown");
        }
    }

    @DisplayName("User does not exist while changing the role")
    @Test
    void failToChangeTheRoleOfAUser() {
        Credentials credentials = new Credentials("test@test.nl", "test", Role.ROLE_USER);

        when(credentialsRepository.findById(credentials.getEmail())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> adminService.changeRole(credentials.getEmail(), Role.ROLE_MOD));
    }
}

