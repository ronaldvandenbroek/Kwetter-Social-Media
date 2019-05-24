package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IUserRepository extends IBaseUUIDRepository<User> {

    List<User> findByName(String name);

    boolean existsByName(String name);

    User findByCredentials(Credentials credentials);

    User findByCredentials_Email(String email);
}
