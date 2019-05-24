package nl.fontys.kwetter.models.dto;

import lombok.Data;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.entity.User;

@Data
public class CredentialsDTO {
    private String email;
    private String password;
    private Role role;
    private User user;

    public CredentialsDTO(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public CredentialsDTO() {
        // Empty constructor
    }
}
