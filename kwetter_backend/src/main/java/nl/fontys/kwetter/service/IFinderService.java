package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;

import java.util.UUID;

public interface IFinderService {

    User getUserById(UUID userID);

    Kwetter getKwetterById(UUID kwetterId);

    Credentials getCredentialsById(String email);
}
