package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.dao.LoginDao;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import java.util.Collection;
import java.util.List;

public class LoginDaoImp implements LoginDao {

    Collection<User> allUsers = InMemoryCollection.users;
    Collection<Credentials> allCredentials = InMemoryCollection.credentials;
    Collection<Kwetter> allKwetters = InMemoryCollection.kwetters;

    LoginDaoImp(){

    }

    @Override
    public User login(Credentials credentials) {
        if (allCredentials.contains(credentials)){
            for (Credentials matchingCredentials : allCredentials) {
                if (matchingCredentials.equals(credentials)) {
                    return matchingCredentials.getUser();
                }
            }
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
