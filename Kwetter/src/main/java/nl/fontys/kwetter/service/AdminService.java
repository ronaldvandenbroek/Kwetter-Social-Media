package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IAdminService;

import java.util.List;

public class AdminService implements IAdminService {
    @Override
    public void changeRole(User user) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
