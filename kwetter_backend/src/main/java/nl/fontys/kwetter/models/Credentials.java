package nl.fontys.kwetter.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Named;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Named
public class Credentials implements Serializable {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    @Id
    @Column(name = "email", updatable = false, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password", updatable = false, nullable = false)
    private String password;

    private Role role;

    @JsonIgnoreProperties("credentials")
    @OneToOne(mappedBy = "credentials", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public Credentials() {
    }

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Credentials(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Credentials(String email, String password, Role role, User user) {
        this.email = email;
        this.password = password;
        this.role = role;
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

    public String showCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName() + " is your name!!!";
    }
}
