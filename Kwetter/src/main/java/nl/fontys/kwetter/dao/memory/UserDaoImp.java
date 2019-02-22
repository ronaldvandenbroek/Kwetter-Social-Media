package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import java.util.Collection;
import java.util.List;

public class UserDaoImp implements UserDao {

    @Override
    public User login(Credentials credentials) {
        if (InMemoryCollection.getAllCredentials().contains(credentials)) {
            for (Credentials matchingCredentials : InMemoryCollection.getAllCredentials()) {
                if (matchingCredentials.equals(credentials)) {
                    return matchingCredentials.getUser();
                }
            }
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) InMemoryCollection.getAllUsers();
    }

    @Override
    public User getUserById(Long userID) {
        Collection<User> allUsers = InMemoryCollection.getAllUsers();
        for (User user : allUsers) {
            if (user.getId().equals(userID)) {
                return new User(user);
            }
        }
        return null;
    }

    @Override
    public boolean createNewUser(Credentials credentials) {
        boolean userNameAlreadyExists = false;
        for (Credentials existingCredentials : InMemoryCollection.getAllCredentials()) {
            if (existingCredentials.getEmail().equals(credentials.getEmail())) {
                userNameAlreadyExists = true;
            }
        }

        if (InMemoryCollection.getAllCredentials().contains(credentials) || userNameAlreadyExists || credentials.getUser() == null) {
            return false;
        } else {
            InMemoryCollection.getAllCredentials().add(credentials);
            credentials.getUser().setId(InMemoryCollection.getNextFreeUserID());
            InMemoryCollection.getAllUsers().add(credentials.getUser());
            return true;
        }
    }

    @Override
    public boolean updateUser(User user) {
        Collection<User> allUsers = InMemoryCollection.getAllUsers();

        if (allUsers.contains(user)) {
            for (User oldUser : allUsers) {
                if (oldUser.equals(user)) {
                    allUsers.remove(oldUser);
                    allUsers.add(user);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        if (InMemoryCollection.getAllUsers().remove(user)) {
            InMemoryCollection.getAllCredentials().remove(user.getCredentials());
            for (Kwetter kwetter : InMemoryCollection.getAllKwetters()) {
                if (kwetter.getOwner().equals(user)) {
                    kwetter.setOwner(null);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean checkIfUsernameDoesntExists(String name) {
        for (User user : InMemoryCollection.getAllUsers()
        ) {
            if (user.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
}
