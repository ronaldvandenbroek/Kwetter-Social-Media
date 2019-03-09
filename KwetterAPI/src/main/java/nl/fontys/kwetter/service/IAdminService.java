package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IAdminService {

    void changeRole(Long userId, Role role) throws UserDoesntExist;

    List<User> getAllUsers();

    //void changePermissionsPerRole();
}
