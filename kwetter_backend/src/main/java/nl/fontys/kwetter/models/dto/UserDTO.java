package nl.fontys.kwetter.models.dto;

import lombok.Data;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;

import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
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

    private Set<Kwetter> createdKwetters;
    private Set<Kwetter> reportedKwetters;
    private Set<Kwetter> heartedKwetters;
    private Set<UserDTO> usersFollowed;
    private Set<UserDTO> followedByUsers;

    public UserDTO() {
        // Empty constructor
    }
}
