package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.User;

import java.util.List;
import java.util.UUID;

public interface IProfileService {
    User updateUser(UserDTO user);

    User updateName(UserDTO user);

    List<User> getFollowers(UUID userID);

    List<User> getFollowing(UUID userID);

    User getFullProfile(UUID userID);

    void followUser(UUID userID, UUID followUserId);

    void unFollowUser(UUID userID, UUID followUserId);
}
