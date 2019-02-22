package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the ProfileService")
class ProfileServiceIntegrationTest {
    private static final String TEST_STRING = "This string is longer that the max length allowed for the name bio language website and location. The bio is still a bit longer but this should be enough.";

    private User testUser;
    private IProfileService profileService;

    @BeforeEach
    void setUp() {
        profileService = new ProfileService();
        ILoginService loginService = new LoginService();

        String email = "0@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(email, password);
        } catch (CannotLoginException | InvalidModelException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        InMemoryCollection.resetMemory();
    }

    @Test
    @DisplayName("User can update the bio")
    void updateBio() {
        String bio = "This is a new test bio";
        String location = "This is a new test location";
        String website = "This is a new test website";

        try {
            User user = profileService.updateBio(testUser.getId(), bio, location, website);
            assertNotNull(user);
            assertEquals(bio, user.getBio());
            assertEquals(location, user.getLocation());
            assertEquals(website, user.getWebsite());
        } catch (InvalidModelException | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update the bio with too long entries")
    void tooLongBio() {
        assertThrows(InvalidModelException.class, () -> profileService.updateBio(testUser.getId(), TEST_STRING, TEST_STRING, TEST_STRING));

        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertNull(user.getLocation());
            assertNull(user.getWebsite());
            assertNull(user.getBio());

        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can update his language")
    void updateLanguage() {
        String language = "Nederlands";

        try {
            User user = profileService.updateLanguage(testUser.getId(), language);
            assertNotNull(user);
            assertEquals(language, user.getLanguage());
        } catch (InvalidModelException | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update the language with too long entries")
    void tooLongLanguage() {
        assertThrows(InvalidModelException.class, () -> profileService.updateLanguage(testUser.getId(), TEST_STRING));

        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertNull(user.getLanguage());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can update its name")
    void updateName() {
        String name = "newTest";

        try {
            User user = profileService.updateName(testUser.getId(), name);
            assertNotNull(user);
            assertEquals(name, user.getName());
        } catch (InvalidModelException | UsernameAlreadyExists | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update its name when it is too long")
    void tooLongName() {
        assertThrows(InvalidModelException.class, () -> profileService.updateName(testUser.getId(), TEST_STRING));

        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertEquals("0Test", user.getName());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update its name when it is already taken")
    void nameAlreadyExists() {
        String name = "1Test";
        assertThrows(UsernameAlreadyExists.class, () -> profileService.updateName(testUser.getId(), name));

        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertEquals("0Test", user.getName());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can get a list of who it is following")
    void getFollowers() {
        try {
            List<User> followers = profileService.getFollowing(testUser.getId());
            assertNotNull(followers);
            assertEquals(9, followers.size());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can get a list of who it is following it")
    void getFollowing() {
        try {
            List<User> followers = profileService.getFollowers(testUser.getId());
            assertNotNull(followers);
            assertEquals(0, followers.size());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can get its full profile")
    void getFullProfile() {
        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertNotNull(user);
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }
}