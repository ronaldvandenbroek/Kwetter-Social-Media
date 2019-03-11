package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.DataLoaderTestConfiguration;
import nl.fontys.kwetter.configuration.H2TestConfiguration;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the Kwetter Service")
@DataJpaTest
@Import({H2TestConfiguration.class, DataLoaderTestConfiguration.class})
@Transactional
class KwetterServiceIntegrationTest {

    private User testUser;

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IKwetterService kwetterService;

    @Autowired
    private IProfileService profileService;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();

        String email = "1@test.nl";
        String password = "test";

        try {
            testUser = loginService.login(new Credentials(email, password));
        } catch (CannotLoginException | InvalidModelException e) {
            e.printStackTrace();
        }

        List<Credentials> credentials = adminService.getAllCredentials();
        List<User> users = adminService.getAllUsers();
        List<Kwetter> kwetters = adminService.getAllKwetters();
    }

    @Test
    @DisplayName("User can create a Kwetter")
    void createKwetterFull() {
        String text = "This is a test kwetter";

        Set<String> tags = new HashSet<>();
        tags.add("TestTag1");
        tags.add("TestTag2");

//        Set<Long> mentionIds = new HashSet<>();
//        mentionIds.add(1L);
//        mentionIds.add(2L);

        Kwetter newKwetter = new Kwetter();
        newKwetter.setText(text);
        newKwetter.setTags(tags);


        try {
            Kwetter kwetter = kwetterService.createKwetter(testUser.getId(), newKwetter);
            List<Kwetter> latestKwetters = kwetterService.getMostRecentKwetters(testUser.getId());

            assertNotNull(kwetter);
            assertEquals(text, kwetter.getText());
            assertEquals(11, latestKwetters.size());
            //assertEquals(2, kwetter.getMentions().size());
            assertEquals(2, kwetter.getTags().size());
        } catch (UserDoesntExist | InvalidModelException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can create a kwetter without tags or mentions")
    void createKwetterMinimal() {
        String text = "This is a test kwetter";

        Kwetter newKwetter = new Kwetter();
        newKwetter.setText(text);

        try {
            Kwetter kwetter = kwetterService.createKwetter(testUser.getId(), newKwetter);

            List<Kwetter> latestKwetters = kwetterService.getMostRecentKwetters(testUser.getId());

            assertNotNull(kwetter);
            assertEquals(text, kwetter.getText());
            assertEquals(11, latestKwetters.size());
            assertEquals(0, latestKwetters.get(0).getMentions().size());
            assertEquals(0, latestKwetters.get(0).getTags().size());
        } catch (UserDoesntExist | InvalidModelException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can't create a kwetter with an invalid text")
    void failToCreateKwetterInvalidText() {
        String text = null;

        Kwetter kwetter = new Kwetter();
        kwetter.setText(text);

        assertThrows(InvalidModelException.class, () -> kwetterService.createKwetter(testUser.getId(), kwetter));
    }

    @Test
    @DisplayName("User can remove a kwetter")
    void removeKwetter() {
        try {

            User user = profileService.getFullProfile(testUser.getId());
            assertEquals(10, user.getCreatedKwetters().size());

            kwetterService.removeKwetter(1L, 1L);

            User user2 = profileService.getFullProfile(testUser.getId());

            assertEquals(9, user2.getCreatedKwetters().size());
        } catch (KwetterDoesntExist | UserDoesntExist kwetterDoesntExist) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can heart a kwetter")
    void heartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), 1L);

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getHeartedKwetters().size());
            Kwetter kwetter = user.getHeartedKwetters().iterator().next();
            assertEquals(1, kwetter.getHearts());

        } catch (KwetterDoesntExist | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a heart from a kwetter")
    void removeHeartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), 1L);
            User user = profileService.getFullProfile(testUser.getId());
            assertEquals(10, user.getCreatedKwetters().size());

            kwetterService.removeHeartKwetter(testUser.getId(), 1L);
            user = profileService.getFullProfile(testUser.getId());

            assertEquals(10, user.getCreatedKwetters().size());
            assertEquals(0, user.getHeartedKwetters().size());

        } catch (KwetterDoesntExist | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can report a kwetter")
    void reportKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), 1L);

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getReportedKwetters().size());
            Kwetter kwetter = user.getReportedKwetters().iterator().next();
            assertEquals(1, kwetter.getReports());

        } catch (KwetterDoesntExist | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a report from a kwetter")
    void removeReportedKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), 1L);

            kwetterService.removeReportKwetter(testUser.getId(), 1L);

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(0, user.getReportedKwetters().size());

        } catch (KwetterDoesntExist | UserDoesntExist e) {
            fail("This exception should not have been thrown");
        }
    }
}