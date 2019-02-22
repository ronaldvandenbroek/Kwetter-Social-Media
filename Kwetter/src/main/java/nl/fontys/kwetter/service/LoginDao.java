package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface LoginDao {
    User login(Credentials credentials);

    List<User> getAllUsers();
}
