package nl.fontys.kwetter.dao.memory.data;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InMemoryCollection {
    private static Collection<User> allUsers;
    private static Collection<Credentials> allCredentials;
    private static Collection<Kwetter> allKwetters;
    private static Long userID;
    private static Long kwetterID;

    private static void createMemory() {
        allUsers = new ArrayList<>();
        allCredentials = new ArrayList<>();
        allKwetters = new ArrayList<>();
        userID = 0L;
        kwetterID = 0L;

        //Create 10 allUsers
        for (int i = 0; i < 10; i++) {
            User user = new User(Role.USER);
            user.setName(i + "Test");
            user.setId(getNextFreeUserID());
            allCredentials.add(new Credentials(i + "@test.nl", "test", user));
            allUsers.add(user);
        }

        //Follow everyone via the first user
        Iterator<User> userIterator = allUsers.iterator();
        User user = userIterator.next();
        while (userIterator.hasNext()) {
            user.follow(userIterator.next());
        }

        //Create Kwetters for the first user
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            Kwetter kwetter = new Kwetter("Test", null, null, user, calendar.getTime());
            kwetter.setId(getNextFreeKwetterID());
            allKwetters.add(kwetter);
        }
    }

    public static Collection<User> getAllUsers() {
        if (allUsers == null) {
            createMemory();
        }
        return allUsers;
    }

    public static Collection<Credentials> getAllCredentials() {
        if (allCredentials == null) {
            createMemory();
        }
        return allCredentials;
    }

    public static Collection<Kwetter> getAllKwetters() {
        if (allKwetters == null) {
            createMemory();
        }
        return allKwetters;
    }

    public static Long getNextFreeUserID() {
        return userID++;
    }

    public static Long getNextFreeKwetterID() {
        return kwetterID++;
    }


    public static void resetMemory() {
        createMemory();
    }
}
