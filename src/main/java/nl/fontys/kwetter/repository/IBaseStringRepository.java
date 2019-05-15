package nl.fontys.kwetter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseStringRepository<T> extends CrudRepository<T, String> {
}
