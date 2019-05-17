package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.repository.IKwetterRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IInMemoryKwetterRepository extends IKwetterRepository {

}
