package nl.fontys.kwetter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface IBaseRepository<T> extends CrudRepository<T, UUID> {
}
