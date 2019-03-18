package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;

import java.util.List;
import java.util.UUID;

public interface IProfileService {
    User updateUser(User user) throws InvalidModelException, UserDoesNotExist;

    User updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesNotExist;

    List<User> getFollowers(UUID userID) throws UserDoesNotExist;

    List<User> getFollowing(UUID userID) throws UserDoesNotExist;

    User getFullProfile(UUID userID) throws UserDoesNotExist;

    void followUser(UUID userID, UUID followUserId) throws UserDoesNotExist;

    void unFollowUser(UUID userID, UUID followUserId) throws UserDoesNotExist;
}
