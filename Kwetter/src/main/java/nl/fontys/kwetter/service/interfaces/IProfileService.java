package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    User updateBio(Long userID, String bio, String location, String website);

    User updateLanguage(Long userID, String language);

    User updatePhoto(Long userID, byte[] photo);

    User updateName(Long userID, String name) throws UsernameAlreadyExists;

    List<User> getFollowers(Long userID);

    List<User> getFollowing(Long userID);

    User getFullProfile(Long userID) throws UserDoesntExist;
}
