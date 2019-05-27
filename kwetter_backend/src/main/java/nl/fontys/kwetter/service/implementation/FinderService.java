package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.repository.IKwetterRepository;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.IFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FinderService implements IFinderService {

    private IUserRepository userRepository;
    private IKwetterRepository kwetterRepository;
    private ICredentialsRepository credentialsRepository;

    @Autowired
    public FinderService(IUserRepository userRepository,
                         IKwetterRepository kwetterRepository,
                         ICredentialsRepository credentialsRepository) {
        this.userRepository = userRepository;
        this.kwetterRepository = kwetterRepository;
        this.credentialsRepository = credentialsRepository;
    }

    /**
     * Get the user via its Id
     *
     * @param userID Id of the User
     * @return The User
     * @throws ModelNotFoundException Thrown when the userID does not have a corresponding user.
     */
    public User getUserById(UUID userID) {
        Optional<User> user = userRepository.findById(userID);
        if (user.isPresent()) {
            return user.get();
        }
        throw new ModelNotFoundException("User with the uuid:" + userID + " could not be found.");
    }

    /**
     * Get the Kwetter via its Id
     *
     * @param kwetterId Id of the User
     * @return The Kwetter
     * @throws ModelNotFoundException Thrown when the kwetterID does not have a corresponding Kwetter.
     */
    public Kwetter getKwetterById(UUID kwetterId) {
        Optional<Kwetter> kwetter = kwetterRepository.findById(kwetterId);
        if (kwetter.isPresent()) {
            return kwetter.get();
        }
        throw new ModelNotFoundException("Cannot find kwetter");
    }

    public Credentials getCredentialsById(String email) {
        Optional<Credentials> credentials = credentialsRepository.findById(email);
        if (credentials.isPresent()) {
            return credentials.get();
        }
        throw new ModelNotFoundException("Credentials with the uuid: " + email + " could not be found.");
    }
}
