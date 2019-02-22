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

public class LoginService implements ILoginService {

    private ModelValidator validator;
    private UserDao userDao;

    public LoginService(){
        userDao = new UserDaoImp();
        validator = new ModelValidator();
    }

    @Override
    public User login(String email, String password) throws CannotLoginException, InvalidModelException {
        Credentials credentials = new Credentials(email, password);
        validator.validate(credentials);

        User user = userDao.login(credentials);
        if (user == null){
            throw new CannotLoginException("No account found matching the credentials");
        }
        return user;
    }

    @Override
    public User createAccount(Credentials credentials) {
        throw new NotImplementedException();
    }

}
