package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.utilities.ModelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service for handling model operations regarding the login process.
 */
@Scope(value = "session")
@Component(value = "loginService")
public class LoginService implements ILoginService {

    private final UserDao userDao;
    private final ModelValidator validator;

    @Autowired
    public LoginService(UserDao userDao, ModelValidator validator) {
        this.userDao = userDao;
        this.validator = validator;
    }

    /**
     * Login
     *
     * @param email    The users email
     * @param password The users password
     * @return The logged in user
     * @throws CannotLoginException  Thrown if the credentials are not correct.
     * @throws InvalidModelException Thrown if the credentials are not valid.
     */
    @Override
    public User login(String email, String password) throws CannotLoginException, InvalidModelException {
        Credentials credentials = new Credentials(email, password);
        validator.validate(credentials);

        User user = userDao.login(credentials);
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
