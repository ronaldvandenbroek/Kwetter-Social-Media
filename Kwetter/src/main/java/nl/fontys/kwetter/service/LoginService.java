package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import nl.fontys.kwetter.utilities.ModelValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Service for handling model operations regarding the login process.
 */
public class LoginService implements ILoginService {

    private ModelValidator validator;
    private UserDao userDao;

    public LoginService() {
        userDao = new UserDaoImp();
        validator = new ModelValidator();
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
