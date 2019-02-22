package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    User updateBio(Long userID, String bio, String location, String website) throws InvalidModelException, UserDoesntExist;

    User updateLanguage(Long userID, String language) throws InvalidModelException, UserDoesntExist;

    User updatePhoto(Long userID, byte[] photo) throws InvalidModelException, UserDoesntExist;

    User updateName(Long userID, String name) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist;

    List<User> getFollowers(Long userID) throws UserDoesntExist;

    List<User> getFollowing(Long userID) throws UserDoesntExist;

    User getFullProfile(Long userID) throws UserDoesntExist;
}
