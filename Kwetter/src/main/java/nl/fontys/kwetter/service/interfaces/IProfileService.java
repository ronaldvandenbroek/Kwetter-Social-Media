package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    User updateBio(Long userID, String bio);

    void updatePhoto(User user);

    void updateName(User user);

    List<User> getFollowers(User user);

    List<User> getFollowing(User user);

    User getFullProfile(User user);
}
