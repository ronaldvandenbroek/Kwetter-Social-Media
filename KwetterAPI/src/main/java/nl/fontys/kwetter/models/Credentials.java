package nl.fontys.kwetter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Credentials)) {
            return false;
        }
        Credentials credentials = (Credentials) o;
        return email.equals(credentials.getEmail()) && password.equals(credentials.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
