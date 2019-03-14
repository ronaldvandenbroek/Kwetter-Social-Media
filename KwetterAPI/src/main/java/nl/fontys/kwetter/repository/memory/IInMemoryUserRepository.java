package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.repository.IUserRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IInMemoryUserRepository extends IUserRepository {

}
