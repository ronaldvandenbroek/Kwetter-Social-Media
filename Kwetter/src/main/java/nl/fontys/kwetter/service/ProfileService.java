package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
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
    public User updateBio(Long userID, String bio, String location, String website) {
        User user = userDao.getUserById(userID);
        user.setBio(bio);
        user.setLocation(location);
        user.setWebsite(website);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updateLanguage(Long userID, String language) {
        User user = userDao.getUserById(userID);
        user.setLanguage(language);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updatePhoto(Long userID, byte[] photo) {
        User user = userDao.getUserById(userID);
        user.setPhoto(photo);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updateName(Long userID, String name) throws UsernameAlreadyExists {
        User user = userDao.getUserById(userID);
        if (userDao.checkIfUsernameDoesntExists(name)) {
            user.setName(name);
            userDao.updateUser(user);
            return user;
        } else{
            throw new UsernameAlreadyExists(name);
        }
    }

    @Override
    public List<User> getFollowers(Long userID) {
        User user = userDao.getUserById(userID);
        return new ArrayList<>(user.getFollowedByUsers());
    }

    @Override
    public List<User> getFollowing(Long userID) {
        User user = userDao.getUserById(userID);
        return new ArrayList<>(user.getUsersFollowed());
    }

    @Override
    public User getFullProfile(Long userID) throws UserDoesntExist {
        User user = userDao.getUserById(userID);
        if (user != null){
            return user;
        }
        else {
            throw new UserDoesntExist();
        }
    }
}
