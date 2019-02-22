package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IAdminService {
    void changeRole(User user);

    List<User> getAllUsers();

    //void changePermissionsPerRole();
}
