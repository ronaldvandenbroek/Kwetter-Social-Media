package nl.fontys.kwetter.repository.memory.data;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Collection;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InMemoryDatabase {
    private static Collection<User> allUsers = new ArrayList<>();
    private static Collection<Credentials> allCredentials = new ArrayList<>();
    private static Collection<Kwetter> allKwetters = new ArrayList<>();
    private static Long userID = 1L;
    private static Long kwetterID = 1L;

    public static Collection<User> userCollection() {
        return allUsers;
    }

    public static Collection<Credentials> credentialsCollection() {
        return allCredentials;
    }

    public static Collection<Kwetter> kwetterCollection() {
        return allKwetters;
    }

    public static Long getNextFreeUserID() {
        return userID++;
    }

    public static Long getNextFreeKwetterID() {
        return kwetterID++;
    }

    public static void clear() {
        allUsers = new ArrayList<>();
        allCredentials = new ArrayList<>();
        allKwetters = new ArrayList<>();
        userID = 1L;
        kwetterID = 1L;
    }
}
