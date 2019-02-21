package nl.fontys.kwetter.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Credentials {

    @Email(message = "Email should be valid")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @NotNull(message = "User cannot be null")
    private User user;

    public Credentials(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;

        user.setCredentials(this);
    }
}
