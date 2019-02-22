package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class LoginService implements ILoginService {

    private UserDao userDao;

    public LoginService(){
        userDao = new UserDaoImp();
    }

    @Override
    public User login(String email, String password) throws CannotLoginException {
        Credentials credentials = new Credentials(email, password);
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
