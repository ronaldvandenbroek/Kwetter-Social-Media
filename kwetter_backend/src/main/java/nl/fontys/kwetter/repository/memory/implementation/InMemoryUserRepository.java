package nl.fontys.kwetter.repository.memory.implementation;

import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.memory.IInMemoryUserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase.credentialsCollection;
import static nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase.userCollection;

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
    public User findByCredentials_Email(String email) {
        Optional<Credentials> foundLogin = credentialsCollection().stream().filter(credentials -> credentials.getEmail().equals(email)).findFirst();
        return foundLogin.map(Credentials::getUser).orElse(null);
    }

    @Override
    public <S extends User> S save(S s) {
        //Check if new user
        if (s.getId() == null && userCollection().stream().anyMatch(user -> user.getName().equals(s.getName()))) {
            return null;
        }

        userCollection().removeIf(user -> user.getId().equals(s.getId()));
        userCollection().add(s);
        Optional<User> first = userCollection().stream().filter(user -> user.getId().equals(s.getId())).findFirst();
        return (S) first.orElse(null);
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userCollection().stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(UUID aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return new ArrayList<>(userCollection());
    }

    @Override
    public Iterable<User> findAllById(Iterable<UUID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return userCollection().size();
    }

    @Override
    public void deleteById(UUID aLong) {
    }

    @Override
    public void delete(User user) {
        userCollection().remove(user);
    }

    @Override
    public void deleteAll(Iterable<? extends User> iterable) {
    }

    @Override
    public void deleteAll() {
        userCollection().clear();
    }

}
