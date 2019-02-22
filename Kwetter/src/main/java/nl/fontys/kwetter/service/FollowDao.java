package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface FollowDao {
    boolean follow(User follower, User following);

    boolean unFollow(User follower, User following);

    List<User> getFollowers(User user);

    List<User> getFollowing(User user);
}
