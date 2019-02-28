package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICredentialsRepository extends CrudRepository<Credentials, Long> {

    List<Credentials> findAllByEmail(String lastName);

    User login(Credentials credentials);
}
