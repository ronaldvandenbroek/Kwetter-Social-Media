package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
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
     * @param credentialsEmail Email of the User
     * @param role             New Role of the User
     * @throws ModelNotFoundException Thrown if the user cannot be found.
     */
    @Override
    public Credentials changeRole(String credentialsEmail, Role role) throws ModelNotFoundException {
        Credentials credentials = getCredentialsById(credentialsEmail);
        credentials.setRole(role);

        credentialsRepository.save(credentials);
        return credentials;
    }

    @Override
    public Credentials getFullCredentials(String email) throws ModelNotFoundException {
        return getCredentialsById(email);
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

    private Credentials getCredentialsById(String email) throws ModelNotFoundException {
        Optional<Credentials> credentials = credentialsRepository.findById(email);
        if (credentials.isPresent()) {
            return credentials.get();
        }
        throw new ModelNotFoundException("Credentials with the id: " + email + " could not be found.");
    }
}
