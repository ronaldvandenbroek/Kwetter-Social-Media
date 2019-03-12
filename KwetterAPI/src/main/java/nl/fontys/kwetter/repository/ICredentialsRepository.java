package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ICredentialsRepository extends CrudRepository<Credentials, Long> {

    List<Credentials> findAllByEmail(String lastName);

//    User login(Credentials credentials);
}
