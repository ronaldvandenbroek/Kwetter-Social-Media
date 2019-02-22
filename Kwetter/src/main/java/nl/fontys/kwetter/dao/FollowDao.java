package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.User;

import java.util.List;

public interface FollowDao {
    void follow(User follower, User following);

    void unFollow(User follower, User following);

    List<User> getFollowers(User user);

    List<User> getFollowing(User user);
}
