package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;

public interface ILoginService {
    User autoLogin() throws LoginException, ModelInvalidException;

    User login(Credentials credentials) throws LoginException, ModelInvalidException;

    User createAccount(String email, String password);
}
