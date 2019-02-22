package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import nl.fontys.kwetter.utilities.ModelValidator;

import java.util.ArrayList;
import java.util.List;

public class ProfileService implements IProfileService {

    private ModelValidator validator;
    private UserDao userDao;

    public ProfileService(){
        userDao = new UserDaoImp();
        validator = new ModelValidator();
    }

    @Override
    public User updateBio(Long userID, String bio, String location, String website) throws InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        user.setBio(bio);
        user.setLocation(location);
        user.setWebsite(website);

        validator.validate(user);

        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updateLanguage(Long userID, String language) throws InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        user.setLanguage(language);

        validator.validate(user);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updatePhoto(Long userID, byte[] photo) throws InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        user.setPhoto(photo);
        validator.validate(user);

        userDao.updateUser(user);
        return user;
    }

    @Override
    public User updateName(Long userID, String name) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        if (userDao.checkIfUsernameDoesntExists(name)) {
            user.setName(name);
            validator.validate(user);
            userDao.updateUser(user);
            return user;
        } else{
            throw new UsernameAlreadyExists(name);
        }
    }

    @Override
    public List<User> getFollowers(Long userID) throws UserDoesntExist {
        User user = getUserById(userID);
        return new ArrayList<>(user.getFollowedByUsers());
    }

    @Override
    public List<User> getFollowing(Long userID) throws UserDoesntExist {
        User user = getUserById(userID);
        return new ArrayList<>(user.getUsersFollowed());
    }

    @Override
    public User getFullProfile(Long userID) throws UserDoesntExist {
        return getUserById(userID);
    }

    private User getUserById(Long userID) throws UserDoesntExist {
        User user = userDao.getUserById(userID);
        if (user == null){
            throw new UserDoesntExist();
        }
        return user;
    }
}

