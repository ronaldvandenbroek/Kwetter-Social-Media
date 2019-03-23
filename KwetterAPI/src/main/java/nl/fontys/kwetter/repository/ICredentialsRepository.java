package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Credentials;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICredentialsRepository extends IBaseStringRepository<Credentials> {

//    List<Credentials> findAllByEmail(String lastName);

//    User jsfLogin(Credentials credentials);
}
