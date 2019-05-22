package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExistsException;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.IProfileService;
import nl.fontys.kwetter.service.IValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for handling model operations regarding the profile.
 */
@Service
public class ProfileService implements IProfileService {

    private IUserRepository userRepository;
    private IValidatorService validator;

    @Autowired
    public ProfileService(IValidatorService validator, IUserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    /**
     * Update the bio of the user.
     *
     * @param user User with the updated user bio
     * @return The updated user
     * @throws ModelInvalidException  Thrown when an invalid input is given for the model.
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateUser(UserDTO user) throws ModelInvalidException, ModelNotFoundException {
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
     * @throws UsernameAlreadyExistsException Thrown if the chosen name already exists.
     * @throws ModelInvalidException          Thrown when an invalid input is given for the model.
     * @throws ModelNotFoundException         Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User updateName(UserDTO user) throws UsernameAlreadyExistsException, ModelNotFoundException, ModelInvalidException {
        if (!userRepository.existsByName(user.getName())) {
            User oldUser = getUserById(user.getId());

            String oldName = oldUser.getName();
            oldUser.setName(user.getName());

            try {
                validator.validate(oldUser);
                return userRepository.save(oldUser);
            } catch (ModelInvalidException e) {
                oldUser.setName(oldName);
                throw e;
            }
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    /**
     * Get all the followers users
     *
     * @param userID Id of the User
     * @return All the users followers
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    @Override
    public List<User> getFollowers(UUID userID) throws ModelNotFoundException {
        User user = getUserById(userID);
        return new ArrayList<>(user.getFollowedByUsers());
    }

    /**
     * Get all the users the user followers
     *
     * @param userID Id of the User
     * @return All users the user follows
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    @Override
    public List<User> getFollowing(UUID userID) throws ModelNotFoundException {
        User user = getUserById(userID);
        return new ArrayList<>(user.getUsersFollowed());
    }

    /**
     * Get the full profile of a user
     *
     * @param userID Id of the User
     * @return The user
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    @Override
    public User getFullProfile(UUID userID) throws ModelNotFoundException {
        return getUserById(userID);
    }

    @Override
    public void followUser(UUID userID, UUID followUserId) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(userID);
        Optional<User> followed = userRepository.findById(followUserId);

        if (user.isPresent() && followed.isPresent()) {
            user.get().follow(followed.get());
            userRepository.save(user.get());
            userRepository.save(followed.get());
        } else {
            throw new ModelNotFoundException("User could not be followed.");
        }
    }

    @Override
    public void unFollowUser(UUID userID, UUID followUserId) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(userID);
        Optional<User> followed = userRepository.findById(followUserId);

        if (user.isPresent() && followed.isPresent()) {
            user.get().removeFollow(followed.get());
            userRepository.save(user.get());
            userRepository.save(followed.get());
        } else {
            throw new ModelNotFoundException("User could not be followed.");
        }
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    private User getUserById(UUID userID) throws ModelNotFoundException {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ModelNotFoundException("User with the id:" + userID + " could not be found.");
    }
}

