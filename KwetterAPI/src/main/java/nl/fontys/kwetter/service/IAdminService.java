package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IAdminService {

    User changeRole(Long userId, Role role) throws UserDoesNotExist;

    List<User> getAllUsers();

    List<Kwetter> getAllKwetters();

    List<Credentials> getAllCredentials();

    //void changePermissionsPerRole();
}
