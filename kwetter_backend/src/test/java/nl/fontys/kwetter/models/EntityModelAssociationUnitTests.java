package nl.fontys.kwetter.models;

import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testing the associations of the models")
class EntityModelAssociationUnitTests {
    private User user;
    private User secondUser;
    private Kwetter kwetter;

    @BeforeEach
    void setUp() {
        user = new User();
        secondUser = new User();
        kwetter = new Kwetter("Test", user, Calendar.getInstance().getTime());
    }

    @Test
    @DisplayName("A created kwetter should be added to the user")
    void createAKwetter() {
        assertEquals(1, user.getCreatedKwetters().size());
        assertTrue(user.getCreatedKwetters().contains(kwetter));
    }

    @Test
    @DisplayName("A created kwetter should be able to be removed")
    void removeAKwetter() {
        boolean success = user.removeCreatedKwetter(kwetter);

        assertTrue(success);
        assertEquals(0, user.getCreatedKwetters().size());
        assertFalse(user.getCreatedKwetters().contains(kwetter));
        assertNull(kwetter.getOwner());
    }

    @Test
    @DisplayName("Clone a kwetter")
    void cloneAKwetter() {
        Kwetter newKwetter = new Kwetter(kwetter);

        assertEquals(kwetter.getUuid(), newKwetter.getUuid());
        assertEquals(kwetter.getHearts(), newKwetter.getHearts());
        assertEquals(kwetter.getReports(), newKwetter.getReports());
        assertEquals(kwetter.getOwner(), newKwetter.getOwner());
        assertEquals(kwetter.getDateTime(), newKwetter.getDateTime());
        assertEquals(kwetter.getText(), newKwetter.getText());
        assertEquals(kwetter.getMentions(), newKwetter.getMentions());
        assertEquals(kwetter.getTags(), newKwetter.getTags());
    }

    @Test
    @DisplayName("A created kwetter cant be removed by someone who is not the owner")
    void failToRemoveUnownedAKwetter() {
        boolean success = secondUser.removeCreatedKwetter(kwetter);

        assertFalse(success);
        assertEquals(1, user.getCreatedKwetters().size());
        assertTrue(user.getCreatedKwetters().contains(kwetter));
        assertEquals(user, kwetter.getOwner());
    }

    @Test
    @DisplayName("A hearted kwetter should be added to the user")
    void heartAKwetter() {
        assertEquals(0, kwetter.getHearts());

        secondUser.addHeartedKwetter(kwetter);

        assertEquals(1, secondUser.getHeartedKwetters().size());
        assertEquals(1, kwetter.getHearts());
    }

    @Test
    @DisplayName("A hearted kwetter should be removed from the user")
    void removeHeartAKwetter() {
        assertEquals(0, kwetter.getHearts());

        secondUser.addHeartedKwetter(kwetter);
        boolean success = secondUser.removeHeartedKwetter(kwetter);

        assertTrue(success);
        assertEquals(0, secondUser.getHeartedKwetters().size());
        assertEquals(0, kwetter.getHearts());
    }

    @Test
    @DisplayName("A hearted kwetter should not be removed if never hearted")
    void failToRemoveHeartAKwetter() {
        assertEquals(0, kwetter.getHearts());

        user.addHeartedKwetter(kwetter);
        boolean success = secondUser.removeHeartedKwetter(kwetter);

        assertFalse(success);
        assertEquals(1, user.getHeartedKwetters().size());
        assertEquals(0, secondUser.getHeartedKwetters().size());
        assertEquals(1, kwetter.getHearts());
    }

    @Test
    @DisplayName("A reported kwetter should be added to the user")
    void reportAKwetter() {
        assertEquals(0, kwetter.getReports());

        secondUser.addReportedKwetter(kwetter);

        assertEquals(1, secondUser.getReportedKwetters().size());
        assertEquals(1, kwetter.getReports());
    }

    @Test
    @DisplayName("A reported kwetter should be removed from the user")
    void removeReportAKwetter() {
        assertEquals(0, kwetter.getReports());

        secondUser.addReportedKwetter(kwetter);
        boolean success = secondUser.removeReportedKwetter(kwetter);

        assertTrue(success);
        assertEquals(0, secondUser.getReportedKwetters().size());
        assertEquals(0, kwetter.getReports());
    }

    @Test
    @DisplayName("A reported kwetter should not be removed if never reported")
    void failToRemoveReportAKwetter() {
        assertEquals(0, kwetter.getReports());

        user.addReportedKwetter(kwetter);
        boolean success = secondUser.removeReportedKwetter(kwetter);

        assertFalse(success);
        assertEquals(1, user.getReportedKwetters().size());
        assertEquals(0, secondUser.getReportedKwetters().size());
        assertEquals(1, kwetter.getReports());
    }

    @Test
    @DisplayName("A user can follow another user")
    void followUser() {
        boolean success = user.follow(secondUser);

        assertTrue(success);
        assertEquals(1, user.getUsersFollowed().size());
        assertEquals(0, user.getFollowedByUsers().size());
        assertEquals(0, secondUser.getUsersFollowed().size());
        assertEquals(1, secondUser.getFollowedByUsers().size());
    }

    @Test
    @DisplayName("A user cant follow its self")
    void failToFollowUserSelf() {
        boolean success = user.follow(user);

        assertFalse(success);
        assertEquals(0, user.getUsersFollowed().size());
        assertEquals(0, user.getFollowedByUsers().size());
    }

    @Test
    @DisplayName("A user can unfollow another user")
    void unfollowUser() {
        user.follow(secondUser);
        boolean success = user.removeFollow(secondUser);

        assertTrue(success);
        assertEquals(0, user.getUsersFollowed().size());
        assertEquals(0, user.getFollowedByUsers().size());
        assertEquals(0, secondUser.getUsersFollowed().size());
        assertEquals(0, secondUser.getFollowedByUsers().size());
    }

    @Test
    @DisplayName("A user can't unfollow a user it never followed")
    void failToUnfollowUserNonExistentUser() {
        boolean success = user.removeFollow(secondUser);

        assertFalse(success);
        assertEquals(0, user.getUsersFollowed().size());
        assertEquals(0, user.getFollowedByUsers().size());
    }

    @Test
    @DisplayName("Clone a User")
    void cloneAUser() {
        User newUser = new User(user);

        assertEquals(user.getId(), newUser.getId());
    }

    @Test
    @DisplayName("Set user credentials")
    void setCredentials() {
        Credentials credentials = new Credentials("test@test.nl", "Test", Role.ROLE_USER, user);

        assertEquals(credentials, user.getCredentials());
    }

    @Test
    @DisplayName("Create credentials without role and user")
    void createCredentials() {
        String email = "test@test.nl";
        String password = "Test";

        Credentials credentials = new Credentials(email, password);
        new CredentialsDTO();

        assertNotNull(credentials);
        assertEquals(email, credentials.getEmail());
        assertEquals(password, credentials.getPassword());
    }
}