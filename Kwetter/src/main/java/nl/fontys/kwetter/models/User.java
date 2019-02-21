package nl.fontys.kwetter.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = {"createdKwetters", "reportedKwetters", "heartedKwetters", "followers", "followings", "credentials"})
public class User {

    private @Id @GeneratedValue Long id;
    private Role role;
    private Credentials credentials;

    private String name;
    private String bio;
    private String website;
    private String location;
    private byte[] photo;

    private Set<Kwetter> createdKwetters;
    private Set<Kwetter> reportedKwetters;
    private Set<Kwetter> heartedKwetters;

    private Set<User> followers;
    private Set<User> followings;

    public User(Role role){
        this.role = role;

        createdKwetters = new HashSet<>();
        reportedKwetters = new HashSet<>();
        heartedKwetters = new HashSet<>();
        followers = new HashSet<>();
        followings = new HashSet<>();
    }

    public void addCreatedKwetter(Kwetter createdKwetter) {
        createdKwetters.add(createdKwetter);
    }

    public void addReportedKwetter(Kwetter reportedKwetter) {
        reportedKwetters.add(reportedKwetter);
    }

    public void addHeartedKwetter(Kwetter heartedKwetter) {
        heartedKwetters.add(heartedKwetter);
    }

    public void follow(User follower) {
        followers.add(follower);
        follower.followedBy(this);
    }

    public void followedBy(User following) {
        followings.add(following);
    }
}
