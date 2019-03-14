package nl.fontys.kwetter.repository.jpa;

import nl.fontys.kwetter.repository.IUserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface IJPAUserRepository extends IUserRepository {
}
