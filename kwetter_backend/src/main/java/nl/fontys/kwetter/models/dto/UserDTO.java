package nl.fontys.kwetter.models.dto;

import lombok.Data;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;

import java.util.Set;
import java.util.UUID;

@Data
public class UserDTO {

    private UUID id = UUID.randomUUID();

    private Credentials credentials;

    private String name;

    private String bio;

    private String website;

    private String location;

    private String language;

    private byte[] photo;

    private Set<Kwetter> createdKwetters;
    private Set<Kwetter> reportedKwetters;
    private Set<Kwetter> heartedKwetters;
    private Set<UserDTO> usersFollowed;
    private Set<UserDTO> followedByUsers;
}
