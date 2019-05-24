package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.entity.Kwetter;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.UUID;

@NoRepositoryBean
public interface IKwetterRepository extends IBaseUUIDRepository<Kwetter> {

    List<Kwetter> findAllByOwnerId(UUID ownerId);

    List<Kwetter> findAllByTextContains(String text);
}
