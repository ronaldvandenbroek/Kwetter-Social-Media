package nl.fontys.kwetter.service;

import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for handling model operations regarding the administrative tasks.
 */
@Service
public class AdminService implements IAdminService {

    private final IUserRepository userRepository;

    @Autowired
    public AdminService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Change the role of a user
     *
     * @param userId Id of the User
     * @param role   New Role of the User
     * @throws UserDoesntExist Thrown if the user cannot be found.
     */
    @Override
    public void changeRole(Long userId, Role role) throws UserDoesntExist {
        User user = getUserById(userId);
        user.setRole(role);

        userRepository.save(user);
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
        throw new UserDoesntExist();
    }
}
