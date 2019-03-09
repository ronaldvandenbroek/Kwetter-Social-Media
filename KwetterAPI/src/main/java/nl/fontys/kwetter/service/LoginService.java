package nl.fontys.kwetter.service;

import nl.fontys.kwetter.repository.ICredentialsRepository;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service for handling model operations regarding the login process.
 */
@Service
public class LoginService implements ILoginService {

    private final ICredentialsRepository credentialsRepository;
    private final IUserRepository userRepository;
    private final ModelValidator validator;

    @Autowired
    public LoginService(ICredentialsRepository credentialsRepository, IUserRepository userRepository, ModelValidator validator) {
        this.credentialsRepository = credentialsRepository;
        this.userRepository = userRepository;
        this.validator = validator;
    }

    /**
     * Login
     *
     * @param credentials The users credentials
     * @return The logged in user
     * @throws CannotLoginException  Thrown if the credentials are not correct.
     * @throws InvalidModelException Thrown if the credentials are not valid.
     */
    @Override
    public User login(Credentials credentials) throws CannotLoginException, InvalidModelException {
        validator.validate(credentials);

        User user = userRepository.findByCredentials(credentials);
        if (user == null) {
            throw new CannotLoginException("No account found matching the credentials");
        }
        return user;
    }

    /**
     * Create a new account
     *
     * @param email    The users email
     * @param password The users password
     * @return The new user
     */
    @Override
    public User createAccount(String email, String password) {
        throw new NotImplementedException();
    }
}
