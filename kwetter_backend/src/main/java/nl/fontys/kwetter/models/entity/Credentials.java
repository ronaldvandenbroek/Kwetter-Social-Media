package nl.fontys.kwetter.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import nl.fontys.kwetter.models.Role;
import nl.fontys.kwetter.models.dto.CredentialsDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
    @Column(name = "email", updatable = false, nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password cannot be null")
    @Column(name = "password", updatable = false, nullable = false)
    private String password;

    private Role role;

    private boolean verified;

    @JsonIgnoreProperties("credentials")
    @OneToOne(mappedBy = "credentials", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    public Credentials() {
        this.verified = false;
    }

    public Credentials(CredentialsDTO credentials) {
        this.verified = false;
        this.email = credentials.getEmail();
        this.password = credentials.getPassword();
        this.role = credentials.getRole();
        this.user = credentials.getUser();
    }

    public Credentials(String email, String password) {
        this(email, password, null);
    }

    public Credentials(String email, String password, Role role) {
        this(email, password, role, null);
    }

    public Credentials(String email, String password, Role role, User user) {
        this.verified = false;
        this.email = email;
        this.password = password;
        this.role = role;
        this.user = user;

        if (user != null) {
            user.setCredentials(this);
        }
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
