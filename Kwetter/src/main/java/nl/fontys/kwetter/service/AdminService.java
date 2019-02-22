package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IAdminService;
import nl.fontys.kwetter.utilities.ModelValidator;

import java.util.List;

public class AdminService implements IAdminService {

    private ModelValidator validator;
    private UserDao userDao;

    public AdminService(){
        userDao = new UserDaoImp();
        validator = new ModelValidator();
    }

    @Override
    public void changeRole(Long userId, Role role) throws UserDoesntExist {
        User user = getUserById(userId);
        user.setRole(role);

        userDao.updateUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    private User getUserById(Long userID) throws UserDoesntExist {
        User user = userDao.getUserById(userID);
        if (user == null){
            throw new UserDoesntExist();
        }
        return user;
    }
}
