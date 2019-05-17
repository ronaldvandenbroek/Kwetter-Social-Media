package nl.fontys.kwetter.repository.memory.implementation.data;

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

    public static Collection<User> userCollection() {
        return allUsers;
    }

    public static Collection<Credentials> credentialsCollection() {
        return allCredentials;
    }

    public static Collection<Kwetter> kwetterCollection() {
        return allKwetters;
    }

    public static void clear() {
        allUsers = new ArrayList<>();
        allCredentials = new ArrayList<>();
        allKwetters = new ArrayList<>();
    }
}
