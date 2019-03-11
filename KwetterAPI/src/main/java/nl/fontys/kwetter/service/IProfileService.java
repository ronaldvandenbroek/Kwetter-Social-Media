package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IProfileService {
    User updateUser(User user) throws InvalidModelException, UserDoesntExist;

    User updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist;

    List<User> getFollowers(Long userID) throws UserDoesntExist;

    List<User> getFollowing(Long userID) throws UserDoesntExist;

    User getFullProfile(Long userID) throws UserDoesntExist;

    void followUser(Long userID, Long followUserId) throws UserDoesntExist;

    void unFollowUser(Long userID, Long followUserId) throws UserDoesntExist;
}
