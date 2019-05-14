package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.repository.ICredentialsRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IInMemoryCredentialsRepository extends ICredentialsRepository {

}
