package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;

public interface ILoginService {
    User login(String email, String password) throws CannotLoginException;

    User createAccount(Credentials credentials);
}
