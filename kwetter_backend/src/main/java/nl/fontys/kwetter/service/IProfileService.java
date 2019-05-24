package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExistsException;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.User;

import java.util.List;
import java.util.UUID;

public interface IProfileService {
    User updateUser(UserDTO user) throws ModelInvalidException, ModelNotFoundException;

    User updateName(UserDTO user) throws UsernameAlreadyExistsException, ModelInvalidException, ModelNotFoundException;

    List<User> getFollowers(UUID userID) throws ModelNotFoundException;

    List<User> getFollowing(UUID userID) throws ModelNotFoundException;

    User getFullProfile(UUID userID) throws ModelNotFoundException;

    void followUser(UUID userID, UUID followUserId) throws ModelNotFoundException;

    void unFollowUser(UUID userID, UUID followUserId) throws ModelNotFoundException;
}
