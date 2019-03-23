package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.*;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the Kwetter Service")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
class KwetterServiceIntegrationTest {

    private User testUser;
    private Kwetter testKwetter;

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
            testUser = loginService.login(new Credentials(email, password, Role.ROLE_USER));
            testKwetter = testUser.getCreatedKwetters().iterator().next();
        } catch (CannotLoginException | InvalidModelException e) {
            e.printStackTrace();
        }
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
        } catch (UserDoesNotExist | InvalidModelException e) {
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
        } catch (UserDoesNotExist | InvalidModelException e) {
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
            kwetterService.removeKwetter(testUser.getId(), testKwetter.getId());

            User user2 = profileService.getFullProfile(testUser.getId());
            Collection<Kwetter> createdKwetters2 = user2.getCreatedKwetters();
            assertEquals(9, createdKwetters2.size());
            assertEquals(9, user2.getCreatedKwetters().size());
        } catch (KwetterDoesNotExist | UserDoesNotExist | CouldNotDelete e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can heart a kwetter")
    void heartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), testKwetter.getId());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getHeartedKwetters().size());
            Kwetter kwetter = user.getHeartedKwetters().iterator().next();
            assertEquals(1, kwetter.getHearts());

        } catch (KwetterDoesNotExist | UserDoesNotExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a heart from a kwetter")
    void removeHeartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), testKwetter.getId());
            User user = profileService.getFullProfile(testUser.getId());
            assertEquals(10, user.getCreatedKwetters().size());

            kwetterService.removeHeartKwetter(testUser.getId(), testKwetter.getId());
            user = profileService.getFullProfile(testUser.getId());

            assertEquals(10, user.getCreatedKwetters().size());
            assertEquals(0, user.getHeartedKwetters().size());

        } catch (KwetterDoesNotExist | UserDoesNotExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can report a kwetter")
    void reportKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), testKwetter.getId());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getReportedKwetters().size());
            Kwetter kwetter = user.getReportedKwetters().iterator().next();
            assertEquals(1, kwetter.getReports());

        } catch (KwetterDoesNotExist | UserDoesNotExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a report from a kwetter")
    void removeReportedKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), testKwetter.getId());

            kwetterService.removeReportKwetter(testUser.getId(), testKwetter.getId());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(0, user.getReportedKwetters().size());

        } catch (KwetterDoesNotExist | UserDoesNotExist e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("Search for a kwetter")
    void searchForAKwetter() {
        String text = "1";

        List<Kwetter> kwetters = kwetterService.searchForKwetter(text);
        assertEquals(2, kwetters.size());
    }
}