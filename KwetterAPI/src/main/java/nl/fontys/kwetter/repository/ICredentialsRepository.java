package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ICredentialsRepository extends IBaseRepository<Credentials> {

    List<Credentials> findAllByEmail(String lastName);

//    User login(Credentials credentials);
}
