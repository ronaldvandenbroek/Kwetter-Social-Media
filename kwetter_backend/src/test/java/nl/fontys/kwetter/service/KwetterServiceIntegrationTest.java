package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.CouldNotDeleteModelException;
import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
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
            testUser = loginService.login(new CredentialsDTO(email, password, Role.ROLE_USER));
            testKwetter = testUser.getCreatedKwetters().iterator().next();
        } catch (LoginException | ModelInvalidException e) {
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

        Kwetter newKwetter = new Kwetter();
        newKwetter.setText(text);
        newKwetter.setTags(tags);

        KwetterDTO newKwetterDTO = new KwetterDTO(newKwetter);

        try {
            Kwetter kwetter = kwetterService.createKwetter(testUser.getId(), newKwetterDTO);

            assertNotNull(kwetter);
            assertEquals(text, kwetter.getText());
            assertEquals(2, kwetter.getTags().size());
        } catch (ModelNotFoundException | ModelInvalidException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can create a kwetter without tags or mentions")
    void createKwetterMinimal() {
        String text = "This is a test kwetter";

        Kwetter newKwetter = new Kwetter();
        newKwetter.setText(text);

        KwetterDTO newKwetterDTO = new KwetterDTO(newKwetter);

        try {
            Kwetter kwetter = kwetterService.createKwetter(testUser.getId(), newKwetterDTO);

            assertNotNull(kwetter);
            assertEquals(text, kwetter.getText());
            assertEquals(0, kwetter.getMentions().size());
            assertEquals(0, kwetter.getTags().size());
        } catch (ModelNotFoundException | ModelInvalidException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can't create a kwetter with an invalid text")
    void failToCreateKwetterInvalidText() {
        String text = null;

        KwetterDTO kwetter = new KwetterDTO();
        kwetter.setText(text);

        assertThrows(ModelInvalidException.class, () -> kwetterService.createKwetter(testUser.getId(), kwetter));
    }

    @Test
    @DisplayName("User can remove a kwetter")
    void removeKwetter() {
        try {
            kwetterService.removeKwetter(testUser.getId(), testKwetter.getUuid());

            User user2 = profileService.getFullProfile(testUser.getId());
            Collection<Kwetter> createdKwetters2 = user2.getCreatedKwetters();
            assertEquals(9, createdKwetters2.size());
            assertEquals(9, user2.getCreatedKwetters().size());
        } catch (ModelNotFoundException | CouldNotDeleteModelException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can heart a kwetter")
    void heartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), testKwetter.getUuid());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getHeartedKwetters().size());
            Kwetter kwetter = user.getHeartedKwetters().iterator().next();
            assertEquals(1, kwetter.getHearts());

        } catch (ModelNotFoundException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a heart from a kwetter")
    void removeHeartKwetter() {
        try {
            kwetterService.heartKwetter(testUser.getId(), testKwetter.getUuid());
            User user = profileService.getFullProfile(testUser.getId());
            assertEquals(10, user.getCreatedKwetters().size());

            kwetterService.removeHeartKwetter(testUser.getId(), testKwetter.getUuid());
            user = profileService.getFullProfile(testUser.getId());

            assertEquals(10, user.getCreatedKwetters().size());
            assertEquals(0, user.getHeartedKwetters().size());

        } catch (ModelNotFoundException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can report a kwetter")
    void reportKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), testKwetter.getUuid());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(1, user.getReportedKwetters().size());
            Kwetter kwetter = user.getReportedKwetters().iterator().next();
            assertEquals(1, kwetter.getReports());

        } catch (ModelNotFoundException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    @DisplayName("User can remove a report from a kwetter")
    void removeReportedKwetter() {
        try {
            kwetterService.reportKwetter(testUser.getId(), testKwetter.getUuid());

            kwetterService.removeReportKwetter(testUser.getId(), testKwetter.getUuid());

            User user = profileService.getFullProfile(testUser.getId());

            assertEquals(0, user.getReportedKwetters().size());

        } catch (ModelNotFoundException e) {
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

    @Test
    @DisplayName("Get the users most recent kwetters")
    void getMostRecent() {
        List<Kwetter> kwetters = kwetterService.getMostRecentKwetters(testUser.getId());
        assertEquals(10, kwetters.size());
    }

    @Test
    @DisplayName("Get the users timeline")
    void getTimeline() {
        List<Kwetter> kwetters = kwetterService.getTimeline(testUser.getId());
        // 10 from own kwetters
        // 1 from 2@Test.nl who 1@Test.nl follows
        assertEquals(11, kwetters.size());
    }
}