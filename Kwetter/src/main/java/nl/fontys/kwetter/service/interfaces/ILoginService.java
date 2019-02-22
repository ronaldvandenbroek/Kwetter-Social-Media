package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.User;

public interface ILoginService {
    User login(String email, String password) throws CannotLoginException, InvalidModelException;

    User createAccount(String email, String password);
}
