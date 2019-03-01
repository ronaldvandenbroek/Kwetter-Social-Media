package nl.fontys.kwetter.service;

import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for handling model operations regarding the profile.
 */
@Service
public class ProfileService implements IProfileService {

    private final ModelValidator validator;
    private final IUserRepository userRepository;

    @Autowired
    public ProfileService(IUserRepository userRepository, ModelValidator validator) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    /**
     * Update the bio of the user.
     *
     * @param user User with the updated user bio
     * @return The updated user
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateUser(User user) throws InvalidModelException, UserDoesntExist {
        User oldUser = getUserById(user.getId());

        validator.validate(user);

        oldUser.setPhoto(user.getPhoto());
        oldUser.setLanguage(user.getLanguage());
        oldUser.setWebsite(user.getWebsite());
        oldUser.setLocation(user.getLocation());
        oldUser.setBio(user.getBio());

        userRepository.save(oldUser);
        return oldUser;
    }

    /**
     * Update the name of the user if it is not already taken/
     *
     * @param user User with the updated name
     * @return The updated user
     * @throws UsernameAlreadyExists Thrown if the chosen name already exists.
     * @throws InvalidModelException Thrown when an invalid input is given for the model.
     * @throws UserDoesntExist       Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist {
        User oldUser = getUserById(user.getId());
        oldUser.setName(user.getName());

        validator.validate(oldUser);

        User savedUser = userRepository.save(oldUser);
        if (savedUser == null){
            throw new UsernameAlreadyExists();
        }
        return savedUser;
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
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserDoesntExist("User with the id:" + userID+ " could not be found.");
    }
}

