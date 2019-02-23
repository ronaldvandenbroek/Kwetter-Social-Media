package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface UserDao {
    User login(Credentials credentials);

    List<User> getAllUsers();

    User getUserById(Long userID);

    boolean createNewUser(Credentials credentials);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean checkIfUsernameDoesntExists(String name);
}
