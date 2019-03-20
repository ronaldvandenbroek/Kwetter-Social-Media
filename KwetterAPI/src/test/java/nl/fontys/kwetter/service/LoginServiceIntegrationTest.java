package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the Login Service")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
class LoginServiceIntegrationTest {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();
    }

    @Test
    @DisplayName("Valid jsfLogin")
    void login() {
        String email = "1@test.nl";
        String password = "test";
        String username = "1Test";

        try {
            User user = loginService.login(new Credentials(email, password));
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

        assertThrows(CannotLoginException.class, () -> loginService.login(new Credentials(email, password)));
    }

    @Test
    @DisplayName("Null email")
    void nullEmail() {
        String email = null;
        String password = "test";

        assertThrows(InvalidModelException.class, () -> loginService.login(new Credentials(email, password)));
    }

    @Test
    @DisplayName("Invalid email")
    void invalidEmail() {
        String email = "NotAnEmail";
        String password = "test";

        assertThrows(InvalidModelException.class, () -> loginService.login(new Credentials(email, password)));
    }

    @Test
    @DisplayName("Null password")
    void nullPassword() {
        String email = "1@test.nl";
        String password = null;

        assertThrows(InvalidModelException.class, () -> loginService.login(new Credentials(email, password)));
    }
}