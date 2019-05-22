package nl.fontys.kwetter.repository.memory.implementation;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.repository.memory.IInMemoryCredentialsRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

import static nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase.credentialsCollection;

@Repository
@Profile("memory")
public class InMemoryCredentialsRepository implements IInMemoryCredentialsRepository {

    @Override
    public <S extends Credentials> S save(S s) {
        credentialsCollection().removeIf(credentials -> credentials.getEmail().equals(s.getEmail()));
        credentialsCollection().add(s);
        Optional<Credentials> savedCredentials = credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(s.getEmail())).findFirst();
        return (S) savedCredentials.orElse(null);
    }

    @Override
    public <S extends Credentials> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Credentials> findById(String s) {
        return credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(s)).findFirst();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<Credentials> findAll() {
        return new ArrayList<>(credentialsCollection());
    }

    @Override
    public Iterable<Credentials> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return credentialsCollection().size();
    }

    @Override
    public void deleteById(String s) {
    }

    @Override
    public void delete(Credentials credentials) {
        credentialsCollection().remove(credentials);
    }

    @Override
    public void deleteAll(Iterable<? extends Credentials> iterable) {
    }

    @Override
    public void deleteAll() {
        credentialsCollection().clear();
    }
}
