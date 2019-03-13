package nl.fontys.kwetter.repository.memory;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.repository.IKwetterRepository;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.data.InMemoryDatabase.getNextFreeKwetterID;
import static nl.fontys.kwetter.repository.memory.data.InMemoryDatabase.kwetterCollection;

@Repository
public class KwetterRepository implements IKwetterRepository {

    @Override
    public List<Kwetter> findAllByOwnerId(Long ownerId) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }

    @Override
    public List<Kwetter> findAllByTextContains(String text) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getText().contains(text)).collect(Collectors.toList());
    }

    @Override
    public <S extends Kwetter> S save(S s) {
        if (s.getId() == null) {
            s.setId(getNextFreeKwetterID());
        }

        kwetterCollection().removeIf(kwetter -> kwetter.getId().equals(s.getId()));
        kwetterCollection().add(s);
        Optional<Kwetter> first = kwetterCollection().stream().filter(kwetter -> kwetter.getId().equals(s.getId())).findFirst();
        return (S) first.get();
    }

    @Override
    public <S extends Kwetter> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Kwetter> findById(Long id) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<Kwetter> findAll() {
        return new ArrayList<>(kwetterCollection());
    }

    @Override
    public Iterable<Kwetter> findAllById(Iterable<Long> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        return kwetterCollection().size();
    }

    @Override
    public void deleteById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public void delete(Kwetter kwetter) {
        kwetterCollection().remove(kwetter);
    }

    @Override
    public void deleteAll(Iterable<? extends Kwetter> iterable) {
        throw new NotImplementedException();
    }

    @Override
    public void deleteAll() {
        kwetterCollection().clear();
    }


}
