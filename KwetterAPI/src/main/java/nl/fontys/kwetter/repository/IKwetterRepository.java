package nl.fontys.kwetter.repository;

import nl.fontys.kwetter.models.Kwetter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface IKwetterRepository extends CrudRepository<Kwetter, Long> {

    List<Kwetter> findAllByOwnerId(Long ownerId);
}
