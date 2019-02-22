package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

public class ProfileService implements IProfileService {

    private Validator validator;
    private UserDao userDao;

    public ProfileService(){
        userDao = new UserDaoImp();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public User updateBio(Long userID, String bio) {
        User user = userDao.getUserById(userID);
        user.setBio(bio);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public void updatePhoto(User user) {

    }

    @Override
    public void updateName(User user) {

    }

    @Override
    public List<User> getFollowers(User user) {
        return null;
    }

    @Override
    public List<User> getFollowing(User user) {
        return null;
    }

    @Override
    public User getFullProfile(User user) {
        return null;
    }
}
