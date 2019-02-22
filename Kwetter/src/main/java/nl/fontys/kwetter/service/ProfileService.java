package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;

import java.util.List;

public class ProfileService implements IProfileService {
    @Override
    public void updateBio(User user) {
        
    }

    @Override
    public void updatePhoto(User user) {

    }

    @Override
    public void updateName(User user) {

    }

    @Override
    public List<User> getFollowers(User user) {
        return null;
    }

    @Override
    public List<User> getFollowing(User user) {
        return null;
    }

    @Override
    public User getFullProfile(User user) {
        return null;
    }
}
