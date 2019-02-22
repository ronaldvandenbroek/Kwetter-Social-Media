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

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Service for handling model operations regarding the login process.
 */
@Stateless
public class LoginService implements ILoginService {

    @Inject
    private UserDao userDao;

    private ModelValidator validator;

    public LoginService() {
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
