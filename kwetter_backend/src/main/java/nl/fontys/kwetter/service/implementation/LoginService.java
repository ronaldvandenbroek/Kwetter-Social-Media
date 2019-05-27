package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.NotImplementedException;
import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.service.ILoginService;
import nl.fontys.kwetter.service.IValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling model operations regarding the jsfLogin process.
 */
@Service
public class LoginService implements ILoginService {

    private IUserRepository userRepository;
    private IValidatorService validator;

    @Autowired
    public LoginService(IValidatorService validator, IUserRepository userRepository) {
        this.validator = validator;
        this.userRepository = userRepository;
    }

    /**
     * Login
     *
     * @param credentials The users credentials
     * @return The logged in user
     * @throws LoginException        Thrown if the credentials are not correct.
     * @throws ModelInvalidException Thrown if the credentials are not valid.
     */
    @Override
    public User login(CredentialsDTO credentials) {
        Credentials credentialsPersistent = new Credentials(credentials);
        validator.validate(credentialsPersistent);

        User user = userRepository.findByCredentials(credentialsPersistent);
        if (user == null) {
            throw new LoginException("No account found matching the credentials");
        }
        if (!credentials.getPassword().equals(user.getCredentials().getPassword())) {
            throw new LoginException("Invalid password");
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
        throw new NotImplementedException("Not Implemented");
    }
}
