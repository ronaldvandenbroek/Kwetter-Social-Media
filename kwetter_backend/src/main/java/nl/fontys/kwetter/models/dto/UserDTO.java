package nl.fontys.kwetter.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.fontys.kwetter.models.entity.User;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO extends ResourceSupport {

    private UUID uuid;

    private byte[] photo;

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

    private int createdKwetterAmount;
    private int reportedKwetterAmount;
    private int heartedKwetterAmount;
    private int usersFollowedAmount;
    private int followedByUsersAmount;

    public UserDTO() {
        // Empty constructor
    }

    public UserDTO(User user) {
        this.uuid = user.getId();
        this.name = user.getName();
        this.bio = user.getBio();
        this.website = user.getWebsite();
        this.location = user.getLocation();
        this.language = user.getLanguage();
        this.photo = user.getPhoto();
        this.createdKwetterAmount = user.getCreatedKwetters().size();
        this.reportedKwetterAmount = user.getReportedKwetters().size();
        this.heartedKwetterAmount = user.getHeartedKwetters().size();
        this.usersFollowedAmount = user.getUsersFollowed().size();
        this.followedByUsersAmount = user.getFollowedByUsers().size();
    }
}
