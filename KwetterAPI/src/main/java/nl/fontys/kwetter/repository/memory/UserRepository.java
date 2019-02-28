package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.repository.IUserRepository;
import nl.fontys.kwetter.models.User;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.data.InMemoryData.getNextFreeUserID;
import static nl.fontys.kwetter.repository.memory.data.InMemoryData.userCollection;

@Repository
public class UserRepository implements IUserRepository {

    @Override
    public List<User> findByName(String name) {
        return userCollection().stream().filter(user -> user.getName().equals(name)).collect(Collectors.toList());
    }

    @Override
    public <S extends User> S save(S s) {
        //Check if new user
        if (s.getId() == null){
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
