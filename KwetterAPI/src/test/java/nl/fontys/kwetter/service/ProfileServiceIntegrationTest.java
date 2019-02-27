package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.memory.IUserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the ProfileService")
class ProfileServiceIntegrationTest {
    private static final String TEST_STRING = "This string is longer that the max length allowed for the name bio language website and location. The bio is still a bit longer but this should be enough.";

    private User testUser;
    private IProfileService profileService;

    @BeforeEach
    void setUp() {
        ModelValidator modelValidator = new ModelValidator();
        IUserDao IUserDao = new UserDaoImp();

        profileService = new ProfileService(modelValidator, IUserDao);
        ILoginService loginService = new LoginService(IUserDao, modelValidator);

        String email = "0@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(new Credentials(email, password));
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
    void updateUser() {
        String bio = "This is a new test bio";
        String location = "This is a new test location";
        String website = "This is a new test website";
        String language = "Nederlands";

        User newUser = new User();
        newUser.setId(testUser.getId());
        newUser.setLocation(location);
        newUser.setWebsite(website);
        newUser.setBio(bio);
        newUser.setLanguage(language);

        try {
            User user = profileService.updateUser(newUser);
            assertNotNull(user);
            assertEquals(bio, user.getBio());
            assertEquals(location, user.getLocation());
            assertEquals(website, user.getWebsite());
            assertEquals(language, user.getLanguage());
        } catch (InvalidModelException | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update the bio with too long entries")
    void tooLongUserUpdates() {
        User newUser = new User();
        newUser.setId(testUser.getId());
        newUser.setLocation(TEST_STRING);
        newUser.setWebsite(TEST_STRING);
        newUser.setBio(TEST_STRING);
        newUser.setLanguage(TEST_STRING);

        assertThrows(InvalidModelException.class, () -> profileService.updateUser(newUser));

        try {
            User user = profileService.getFullProfile(testUser.getId());
            assertNull(user.getLocation());
            assertNull(user.getWebsite());
            assertNull(user.getBio());
            assertNull(user.getLanguage());
        } catch (UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can update its name")
    void updateName() {
        String name = "newTest";

        User newUser = new User();
        newUser.setId(testUser.getId());
        newUser.setName(name);

        try {
            User user = profileService.updateName(newUser);
            assertNotNull(user);
            assertEquals(name, user.getName());
        } catch (InvalidModelException | UsernameAlreadyExists | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User cant update its name when it is too long")
    void tooLongName() {
        User newUser = new User();
        newUser.setId(testUser.getId());
        newUser.setName(TEST_STRING);

        assertThrows(InvalidModelException.class, () -> profileService.updateName(newUser));

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

        User newUser = new User();
        newUser.setId(testUser.getId());
        newUser.setName(name);

        assertThrows(UsernameAlreadyExists.class, () -> profileService.updateName(newUser));

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