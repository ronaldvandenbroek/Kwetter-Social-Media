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
import java.util.UUID;

/**
 * Service for handling model operations regarding the administrative tasks.
 */
@Service
public class AdminService implements IAdminService {

    private IUserRepository userRepository;
    private IKwetterRepository kwetterRepository;
    private ICredentialsRepository credentialsRepository;

    @Autowired
    public AdminService(IUserRepository userRepository, IKwetterRepository kwetterRepository, ICredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.kwetterRepository = kwetterRepository;
        this.credentialsRepository = credentialsRepository;
    }

    /**
     * Change the role of a user
     *
     * @param userId Id of the User
     * @param role   New Role of the User
     * @throws UserDoesNotExist Thrown if the user cannot be found.
     */
    @Override
    public User changeRole(UUID userId, Role role) throws UserDoesNotExist {
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
    private User getUserById(UUID userID) throws UserDoesNotExist {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UserDoesNotExist("User with the id:" + userID + " could not be found.");
    }
}
