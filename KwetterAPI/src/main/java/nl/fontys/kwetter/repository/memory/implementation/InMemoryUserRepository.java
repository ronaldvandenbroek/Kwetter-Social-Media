package nl.fontys.kwetter.repository.memory.implementation;

import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.IInMemoryUserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase.*;

@Repository
@Profile("memory")
public class InMemoryUserRepository implements IInMemoryUserRepository {

    @Override
    public List<User> findByName(String name) {
        return userCollection().stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public boolean existsByName(String name) {
        return userCollection().stream().anyMatch(user -> user.getName().equals(name));
    }

    @Override
    public User findByCredentials(Credentials loginCredentials) {
        Optional<Credentials> foundLogin = credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(loginCredentials.getEmail()) && credentials.getPassword().equals(loginCredentials.getPassword())).findFirst();
        return foundLogin.map(Credentials::getUser).orElse(null);
    }

    @Override
    public <S extends User> S save(S s) {
        //Check if new user
        if (s.getId() == null) {
            s.setId(getNextFreeUserID());
            if (userCollection().stream().anyMatch(user -> user.getName().equals(s.getName()))) {
                return null;
            }
        }

        userCollection().removeIf(user -> user.getId().equals(s.getId()));
        userCollection().add(s);
        Optional<User> first = userCollection().stream().filter(user -> user.getId().equals(s.getId())).findFirst();
        return (S) first.get();
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userCollection().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<User> findAll() {
        return new ArrayList<>(userCollection());
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        return userCollection().size();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(User user) {
        userCollection().remove(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        userCollection().clear();
    }

}
