package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IUserRepository extends CrudRepository<User, Long> {

    List<User> findByName(String name);

    boolean existsByName(String name);

    User findByCredentials(Credentials credentials);
}
