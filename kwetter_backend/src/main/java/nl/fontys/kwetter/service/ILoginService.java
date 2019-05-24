package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.NotImplementedException;
import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.entity.User;

public interface ILoginService {
    User autoLogin() throws LoginException, ModelInvalidException;

    User login(CredentialsDTO credentials) throws LoginException, ModelInvalidException;

    User createAccount(String email, String password) throws NotImplementedException;
}
