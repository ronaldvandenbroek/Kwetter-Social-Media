package nl.fontys.kwetter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface IBaseStringRepository<T> extends CrudRepository<T, String> {
}
