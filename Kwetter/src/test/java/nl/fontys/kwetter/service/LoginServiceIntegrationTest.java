package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the Login Service")
class LoginServiceIntegrationTest {

    private ILoginService loginService;

    @BeforeEach
    void setUp() {
        loginService = new LoginService();
    }

    @AfterEach
    void tearDown() {
        InMemoryCollection.resetMemory();
    }

    @Test
    @DisplayName("Valid login")
    void login() {
        String email = "1@test.nl";
        String password = "test";
        String username = "1Test";

        try {
            User user = loginService.login(email, password);
            assertNotNull(user);
            assertEquals(email, user.getCredentials().getEmail());
            assertEquals(password, user.getCredentials().getPassword());
            assertEquals(username, user.getName());
        } catch (CannotLoginException | InvalidModelException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("Invalid user")
    void invalidUser() {
        String email = "WrongEmail@test.nl";
        String password = "test";

        assertThrows(CannotLoginException.class, () -> loginService.login(email, password));
    }

    @Test
    @DisplayName("Null email")
    void nullEmail() {
        String email = null;
        String password = "test";

        assertThrows(InvalidModelException.class, () -> loginService.login(email, password));
    }

    @Test
    @DisplayName("Invalid email")
    void invalidEmail() {
        String email = "NotAnEmail";
        String password = "test";

        assertThrows(InvalidModelException.class, () -> loginService.login(email, password));
    }

    @Test
    @DisplayName("Null password")
    void nullPassword() {
        String email = "1@test.nl";
        String password = null;

        assertThrows(InvalidModelException.class, () -> loginService.login(email, password));
    }
}