package nl.fontys.kwetter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(exclude = {"user"})
@Entity
public class Credentials implements Serializable {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Id
    private String email;

    @NotNull(message = "Password cannot be null")
    private String password;

    @JsonIgnoreProperties("credentials")
    @OneToOne(mappedBy = "credentials", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
