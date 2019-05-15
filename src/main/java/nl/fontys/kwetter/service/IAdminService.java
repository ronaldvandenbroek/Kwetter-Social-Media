package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IAdminService {

    Credentials changeRole(String credentialsEmail, Role role) throws ModelNotFoundException;

    Credentials getFullCredentials(String email) throws ModelNotFoundException;

    List<User> getAllUsers();

    List<Kwetter> getAllKwetters();

    List<Credentials> getAllCredentials();

    //void changePermissionsPerRole();
}
