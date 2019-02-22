package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    void updateBio(User user);

    void updatePhoto(User user);

    void updateName(User user);

    List<User> getFollowers(User user);

    List<User> getFollowing(User user);

    User getFullProfile(User user);
}
