package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling model operations regarding the administrative tasks.
 */
@Service
public class AdminService implements IAdminService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IKwetterRepository kwetterRepository;

    @Autowired
    private ICredentialsRepository credentialsRepository;

    public AdminService() {
    }

    /**
     * Change the role of a user
     *
     * @param userId Id of the User
     * @param role   New Role of the User
     * @throws UserDoesNotExist Thrown if the user cannot be found.
     */
    @Override
    public User changeRole(Long userId, Role role) throws UserDoesNotExist {
        User user = getUserById(userId);
        user.setRole(role);

        userRepository.save(user);
        return user;
    }

    /**
     * Get a list of all users
     *
     * @return list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public List<Kwetter> getAllKwetters() {
        return (List<Kwetter>) kwetterRepository.findAll();
    }

    @Override
    public List<Credentials> getAllCredentials() {
        return (List<Credentials>) credentialsRepository.findAll();
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws UserDoesNotExist Thrown when the userID does not have a corresponding user.
     */
    private User getUserById(Long userID) throws UserDoesNotExist {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserDoesNotExist("User with the id:" + userID + " could not be found.");
    }
}
