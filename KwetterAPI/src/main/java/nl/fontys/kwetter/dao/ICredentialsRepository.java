package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ICredentialsRepository extends CrudRepository<Credentials, Long> {

    List<Credentials> findAllByEmail(String lastName);
}
