package nl.fontys.kwetter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(exclude = {"user"})
public class Credentials {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @JsonIgnoreProperties("credentials")
    private User user;

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;

        user.setCredentials(this);
    }
}
