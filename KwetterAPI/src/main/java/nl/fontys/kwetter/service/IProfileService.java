package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    User updateUser(User user) throws InvalidModelException, UserDoesNotExist;

    User updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesNotExist;

    List<User> getFollowers(Long userID) throws UserDoesNotExist;

    List<User> getFollowing(Long userID) throws UserDoesNotExist;

    User getFullProfile(Long userID) throws UserDoesNotExist;

    void followUser(Long userID, Long followUserId) throws UserDoesNotExist;

    void unFollowUser(Long userID, Long followUserId) throws UserDoesNotExist;
}
