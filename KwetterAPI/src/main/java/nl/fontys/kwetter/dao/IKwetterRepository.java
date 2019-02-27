package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.Kwetter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IKwetterRepository extends CrudRepository<Kwetter, Long> {

    List<Kwetter> findAllByOwnerId(Long ownerId);
}
