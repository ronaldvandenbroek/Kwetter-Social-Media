package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Kwetter;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface IKwetterRepository extends IBaseRepository<Kwetter> {

    List<Kwetter> findAllByOwnerId(Long ownerId);

    List<Kwetter> findAllByTextContains(String text);
}
