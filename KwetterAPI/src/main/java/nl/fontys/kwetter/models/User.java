package nl.fontys.kwetter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.*;

@Data
@Entity
@EqualsAndHashCode(exclude = {"createdKwetters", "reportedKwetters", "heartedKwetters", "usersFollowed", "followedByUsers", "credentials", "bio", "role", "name"})
@ToString(exclude = {"createdKwetters", "reportedKwetters", "heartedKwetters", "usersFollowed", "followedByUsers", "credentials"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Role role;

    @JsonIgnoreProperties("user")
    @OneToOne(fetch = FetchType.LAZY)
    private Credentials credentials;

    @Size(max = 50)
    private String name;

    @Size(max = 140)
    private String bio;

    @Size(max = 50)
    private String website;

    @Size(max = 50)
    private String location;

    @Size(max = 50)
    private String language;

    private byte[] photo;

    @JsonIgnoreProperties({"owner", "mentions"})
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = {PERSIST})
    private Set<Kwetter> createdKwetters;

    @JsonIgnoreProperties({"owner", "mentions"})
    @ManyToMany(cascade = PERSIST)
    private Set<Kwetter> reportedKwetters;

    @JsonIgnoreProperties({"owner", "mentions"})
    @ManyToMany(cascade = PERSIST)
    private Set<Kwetter> heartedKwetters;

    @JsonIgnoreProperties({"usersFollowed", "followedByUsers"})
    @ManyToMany(cascade = PERSIST)
    @JoinTable(name = "follows",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "followed_id")})
    private Set<User> usersFollowed;

    @JsonIgnoreProperties({"usersFollowed", "followedByUsers"})
    @ManyToMany(cascade = PERSIST)
    private Set<User> followedByUsers;

    public User() {
    }

    public User(User toBeClonedUser) {
        this.id = toBeClonedUser.getId();
        this.role = toBeClonedUser.getRole();
        this.credentials = toBeClonedUser.getCredentials();
        this.name = toBeClonedUser.getName();
        this.bio = toBeClonedUser.getBio();
        this.website = toBeClonedUser.getWebsite();
        this.location = toBeClonedUser.getLocation();
        this.language = toBeClonedUser.getLanguage();
        this.photo = toBeClonedUser.getPhoto();
        this.createdKwetters = toBeClonedUser.getCreatedKwetters();
        this.reportedKwetters = toBeClonedUser.getReportedKwetters();
        this.heartedKwetters = toBeClonedUser.getHeartedKwetters();
        this.usersFollowed = toBeClonedUser.getUsersFollowed();
        this.followedByUsers = toBeClonedUser.getFollowedByUsers();
    }

    public User(Role role) {
        this.role = role;

        createdKwetters = new HashSet<>();
        reportedKwetters = new HashSet<>();
        heartedKwetters = new HashSet<>();
        usersFollowed = new HashSet<>();
        followedByUsers = new HashSet<>();
    }

    public User(Role role, Long tempId) {
        this(role);
        this.id = tempId;
    }

    public void addCreatedKwetter(Kwetter createdKwetter) {
        createdKwetters.add(createdKwetter);
    }

    public boolean removeCreatedKwetter(Kwetter createdKwetter) {
        if (createdKwetters.remove(createdKwetter)) {
            createdKwetter.removeOwner();
            return true;
        }
        return false;
    }

    public void addReportedKwetter(Kwetter reportedKwetter) {
        reportedKwetters.add(reportedKwetter);
        reportedKwetter.report();
    }

    public boolean removeReportedKwetter(Kwetter reportedKwetter) {
        if (reportedKwetters.remove(reportedKwetter)) {
            reportedKwetter.removeReport();
            return true;
        }
        return false;
    }

    public void addHeartedKwetter(Kwetter heartedKwetter) {
        heartedKwetters.add(heartedKwetter);
        heartedKwetter.heart();
    }

    public boolean removeHeartedKwetter(Kwetter heartedKwetter) {
        if (heartedKwetters.remove(heartedKwetter)) {
            heartedKwetter.removeHeart();
            return true;
        }
        return false;
    }

    public boolean follow(User follower) {
        if (!this.equals(follower)) {
            usersFollowed.add(follower);
            follower.followedBy(this);
            return true;
        }
        return false;
    }

    public boolean removeFollow(User follower) {
        if (usersFollowed.remove(follower)) {
            follower.removeFollowedBy(this);
            return true;
        }
        return false;
    }

    public void followedBy(User following) {
        followedByUsers.add(following);
    }

    public void removeFollowedBy(User following) {
        followedByUsers.remove(following);
    }
}
