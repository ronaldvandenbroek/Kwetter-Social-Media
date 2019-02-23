package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for handling model operations regarding the profile.
 */
@Scope(value = "session")
@Component(value = "profileService")
public class ProfileService implements IProfileService {

    private final ModelValidator validator;
    private final UserDao userDao;

    @Autowired
    public ProfileService(ModelValidator validator, UserDao userDao) {
        this.validator = validator;
        this.userDao = userDao;
    }

    /**
     * Update the bio of the user.
     *
     * @param userID   Id of the user
     * @param bio      Updated bio
     * @param location Updated location
     * @param website  Updated website
     * @return The updated user
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
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

    /**
     * Update the language of the user.
     *
     * @param userID   Id of the user
     * @param language Updated language
     * @return The updated user
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateLanguage(Long userID, String language) throws InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        user.setLanguage(language);

        validator.validate(user);
        userDao.updateUser(user);
        return user;
    }

    /**
     * Update the photo of the user.
     *
     * @param userID Id of the user.
     * @param photo  Updated photo.
     * @return The updated user.
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updatePhoto(Long userID, byte[] photo) throws InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        user.setPhoto(photo);
        validator.validate(user);

        userDao.updateUser(user);
        return user;
    }

    /**
     * Update the name of the user if it is not already taken/
     *
     * @param userID Id of the user
     * @param name   Updated name
     * @return The updated user
     * @throws UsernameAlreadyExists Thrown if the chosen name already exists.
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateName(Long userID, String name) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist {
        User user = getUserById(userID);
        if (userDao.checkIfUsernameDoesntExists(name)) {
            user.setName(name);
            validator.validate(user);
            userDao.updateUser(user);
            return user;
        } else {
            throw new UsernameAlreadyExists(name);
        }
    }

    /**
     * Get all the followers users
     *
     * @param userID Id of the User
     * @return All the users followers
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding user.
     */
    @Override
    public List<User> getFollowers(Long userID) throws UserDoesntExist {
        User user = getUserById(userID);
        return new ArrayList<>(user.getFollowedByUsers());
    }

    /**
     * Get all the users the user followers
     *
     * @param userID Id of the User
     * @return All users the user follows
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding user.
     */
    @Override
    public List<User> getFollowing(Long userID) throws UserDoesntExist {
        User user = getUserById(userID);
        return new ArrayList<>(user.getUsersFollowed());
    }

    /**
     * Get the full profile of a user
     *
     * @param userID Id of the User
     * @return The user
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User getFullProfile(Long userID) throws UserDoesntExist {
        return getUserById(userID);
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws UserDoesntExist Thrown when the userID does not have a corresponding user.
     */
    private User getUserById(Long userID) throws UserDoesntExist {
        User user = userDao.getUserById(userID);
        if (user == null) {
            throw new UserDoesntExist();
        }
        return user;
    }
}

