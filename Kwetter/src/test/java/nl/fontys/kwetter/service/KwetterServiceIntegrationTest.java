package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IKwetterService;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class KwetterServiceIntegrationTest {

    private User testUser;
    private IKwetterService kwetterService;

    @BeforeEach
    void setUp() {
        kwetterService = new KwetterService();

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
    void createKwetter() {
        String text = "This is a test kwetter";

        Set<String> tags = new HashSet<>();
        tags.add("TestTag1");
        tags.add("TestTag2");

        Set<Long> mentionIds = new HashSet<>();
        mentionIds.add(1L);
        mentionIds.add(2L);

        try {
            Kwetter kwetter = kwetterService.createKwetter(testUser.getId(), text, tags, mentionIds);
            List<Kwetter> latestKwetters = kwetterService.getMostRecentKwetters(testUser.getId());

            assertNotNull(kwetter);
            assertEquals(text, kwetter.getText());
            assertEquals(11, latestKwetters.size());
            assertEquals(2, latestKwetters.get(0).getMentions().size());
            assertEquals(2, latestKwetters.get(0).getTags().size());
        } catch (UserDoesntExist | InvalidModelException e) {
            fail("This exception should not have been thrown");
        }
    }

    @Test
    void removeKwetter() {
    }

    @Test
    void heartKwetter() {
    }

    @Test
    void removeHeartKwetter() {
    }

    @Test
    void reportKwetter() {
    }

    @Test
    void removeReportKwetter() {
    }

    @Test
    void getMentionedKwetters() {
    }

    @Test
    void getMostRecentKwetters() {
    }

    @Test
    void getHeartedKwetters() {
    }
}