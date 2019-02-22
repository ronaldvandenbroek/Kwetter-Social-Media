package nl.fontys.kwetter.service;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.dao.memory.UserDaoImp;
import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public class LoginService implements ILoginService {

    private Validator validator;
    private UserDao userDao;

    public LoginService(){
        userDao = new UserDaoImp();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Override
    public User login(String email, String password) throws CannotLoginException, InvalidModelException {
        Credentials credentials = new Credentials(email, password);
        validateCredentials(credentials);

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

    private void validateCredentials(Credentials credentials) throws InvalidModelException {
        Set<ConstraintViolation<Credentials>> violations = validator.validate(credentials);

        StringBuilder violationMessages = new StringBuilder();
        for (ConstraintViolation<Credentials> violation : violations) {
            violationMessages.append(violation.getMessage()).append(" ");
        }
        if (violationMessages.length() != 0){
            throw new InvalidModelException(violationMessages.toString());
        }
    }
}
