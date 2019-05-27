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
import nl.fontys.kwetter.service.IFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for handling model operations regarding the administrative tasks.
 */
@Service
public class AdminService implements IAdminService {

    private IUserRepository userRepository;
    private IKwetterRepository kwetterRepository;
    private ICredentialsRepository credentialsRepository;
    private IFinderService finderService;

    @Autowired
    public AdminService(IUserRepository userRepository,
                        IKwetterRepository kwetterRepository,
                        ICredentialsRepository credentialsRepository,
                        IFinderService finderService) {
        this.userRepository = userRepository;
        this.kwetterRepository = kwetterRepository;
        this.credentialsRepository = credentialsRepository;
        this.finderService = finderService;
    }

    /**
     * Change the role of a user
     *
     * @param credentialsEmail Email of the User
     * @param role             New Role of the User
     * @throws ModelNotFoundException Thrown if the user cannot be found.
     */
    @Override
    public Credentials changeRole(String credentialsEmail, Role role) {
        Credentials credentials = finderService.getCredentialsById(credentialsEmail);
        credentials.setRole(role);

        credentialsRepository.save(credentials);
        return credentials;
    }

    @Override
    public Credentials getFullCredentials(String email) {
        return finderService.getCredentialsById(email);
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
}
