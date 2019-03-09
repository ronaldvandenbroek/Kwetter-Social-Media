package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.repository.ICredentialsRepository;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.data.InMemoryDatabase.credentialsCollection;

@Repository
public class CredentialsRepository implements ICredentialsRepository {

    @Override
    public List<Credentials> findAllByEmail(String lastName) {
        return credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(lastName)).collect(Collectors.toList());
    }

//    @Override
//    public User login(Credentials loginCredentials) {
//        Optional<Credentials> foundLogin = credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(loginCredentials.getEmail()) && credentials.getPassword().equals(loginCredentials.getPassword())).findFirst();
//        return foundLogin.map(Credentials::getUser).orElse(null);
//    }

    @Override
    public <S extends Credentials> S save(S s) {
        credentialsCollection().removeIf(credentials -> credentials.getEmail().equals(s.getEmail()));
        credentialsCollection().add(s);
        Optional<Credentials> savedCredentials = credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(s.getEmail())).findFirst();
        return (S) savedCredentials.orElse(null);
    }

    @Override
    public <S extends Credentials> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Credentials> findById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Credentials> findAll() {
        return new ArrayList<>(credentialsCollection());
    }

    @Override
    public Iterable<Credentials> findAllById(Iterable<Long> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        return credentialsCollection().size();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Credentials credentials) {
        credentialsCollection().remove(credentials);
    }

    @Override
    public void deleteAll(Iterable<? extends Credentials> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        credentialsCollection().clear();
    }
}
