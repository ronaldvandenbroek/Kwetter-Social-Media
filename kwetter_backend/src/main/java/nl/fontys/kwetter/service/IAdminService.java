package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;

import java.util.List;

public interface IAdminService {

    Credentials changeRole(String credentialsEmail, Role role);

    Credentials getFullCredentials(String email);

    List<User> getAllUsers();

    List<Kwetter> getAllKwetters();

    List<Credentials> getAllCredentials();
}
