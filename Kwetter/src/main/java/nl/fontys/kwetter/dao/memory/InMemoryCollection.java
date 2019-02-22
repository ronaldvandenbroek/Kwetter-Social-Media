package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

import java.util.*;

public class InMemoryCollection {
    static Collection<User> users;
    static Collection<Credentials> credentials;
    static Collection<Kwetter> kwetters;

    public InMemoryCollection(){
        resetMemory();
    }

    public static void resetMemory(){
        users = new ArrayList<>();
        credentials = new ArrayList<>();

        //Create 10 allUsers
        for (int i = 0; i < 10; i++) {
            User user = new User(Role.USER);
            credentials.add(new Credentials(i + "@test.nl", "test", user));
            users.add(new User(Role.USER));
        }

        //Follow everyone via the first user
        Iterator<User> userIterator = users.iterator();
        User user = userIterator.next();
        while (userIterator.hasNext()){
            user.follow(userIterator.next());
        }

        //Create Kwetters for the first user
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            Kwetter kwetter = new Kwetter("Test", null, null, user, calendar.getTime());
            user.addCreatedKwetter(kwetter);
            kwetters.add(kwetter);
        }
    }
}
