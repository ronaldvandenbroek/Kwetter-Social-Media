package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.entity.User;

public interface ILoginService {
    User login(CredentialsDTO credentials);

    User createAccount(String email, String password);
}
