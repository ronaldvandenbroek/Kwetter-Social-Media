package nl.fontys.kwetter.repository.memory.implementation.data.manager;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

@Service
public class InMemoryDatabaseManager implements IInMemoryDatabaseManager {

    private IKwetterRepository kwetterRepository;
    private IUserRepository userRepository;
    private ICredentialsRepository credentialsRepository;

    @Autowired
    public InMemoryDatabaseManager(IUserRepository userRepository, IKwetterRepository kwetterRepository, ICredentialsRepository credentialsRepository) {
        this.kwetterRepository = kwetterRepository;
        this.userRepository = userRepository;
        this.credentialsRepository = credentialsRepository;
    }

    public void reset() {
        InMemoryDatabase.clear();

        Collection<User> presetUsers = new ArrayList<>();
        Collection<Credentials> presetCredentials = new ArrayList<>();
        Collection<Kwetter> presetKwetters = new ArrayList<>();

        //Create 10 allUsers
        for (int i = 1; i < 11; i++) {
            User user = new User();
            user.setName(i + "Test");
            userRepository.save(user);

            Credentials credentials = new Credentials(i + "@test.nl", "test", Role.ROLE_USER, user);

            presetCredentials.add(credentials);
            presetUsers.add(user);

            credentialsRepository.save(credentials);
        }

        //Follow everyone via the first user
        Iterator<User> userIterator = presetUsers.iterator();
        User user = userIterator.next();
        User secondUser = null;
        boolean followBack = false;
        while (userIterator.hasNext()) {
            User nextUser = userIterator.next();
            if (!followBack) {
                nextUser.follow(user);
                secondUser = nextUser;
                followBack = true;
            }
            user.follow(nextUser);
        }
        userRepository.save(user);

        //Create Kwetters for the first user
        Calendar calendar = Calendar.getInstance();
        for (int i = 1; i < 11; i++) {
            Kwetter kwetter = new Kwetter(i + "Test", null, null, user, calendar.getTime());

            presetKwetters.add(kwetter);

            kwetterRepository.save(kwetter);
        }

        // Create a test kwetter for the second user
        if (secondUser != null) {
            Kwetter kwetter = new Kwetter(secondUser.getName() + "Test", null, null, secondUser, calendar.getTime());
            presetKwetters.add(kwetter);

            kwetterRepository.save(kwetter);
        }
    }
}
