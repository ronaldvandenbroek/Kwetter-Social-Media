package nl.fontys.kwetter.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing the relations of the User Model")
class UserTest {
    private Calendar calendar;

    @BeforeEach
    void setUp() {
//        users = new ArrayList<>();
//        credentials = new ArrayList<>();
//
//        //Create users
//        for (int i = 0; i < 10; i++) {
//            users.add(new User(i, Role.USER));
//        }
//
//        //Set followers
//        users.get(1).follow(users.get(2));
//        users.get(1).follow(users.get(3));
//        users.get(1).follow(users.get(4));
//        users.get(2).follow(users.get(1));
//        users.get(2).follow(users.get(2));
//
//        //Add credentials to users
//        for (int i = 0; i < 10; i++) {
//            credentials.add(new Credentials(i + "@test.nl", "test", users.get(i)));
//        }
//
//        //Add Kwetters
//        Calendar calendar = Calendar.getInstance();
//        for (User user : users) {
//            for (int i = 0; i < 10; i++) {
//                user.addCreatedKwetter(new Kwetter(i, "Test", null, null, user, calendar.getTime()));
//            }
//        }

        calendar = Calendar.getInstance();
    }

    @Test
    @DisplayName("Kwetter should be added to the user")
    void createAKwetter() {
        User user = new User(Role.USER);
        Kwetter kwetter = new Kwetter("Test", null, null, user, calendar.getTime());

        assertEquals(1, user.getCreatedKwetters().size());
        assertTrue(user.getCreatedKwetters().contains(kwetter));
    }
}