package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

import java.util.List;
import java.util.UUID;

public interface IAdminService {

    Credentials changeRole(String credentialsEmail, Role role) throws UserDoesNotExist;

    Credentials getFullCredentials(String email) throws UserDoesNotExist;

    List<User> getAllUsers();

    List<Kwetter> getAllKwetters();

    List<Credentials> getAllCredentials();

    //void changePermissionsPerRole();
}
