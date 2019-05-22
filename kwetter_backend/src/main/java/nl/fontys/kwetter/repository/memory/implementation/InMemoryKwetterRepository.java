package nl.fontys.kwetter.repository.memory.implementation;

import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.repository.memory.IInMemoryKwetterRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase.kwetterCollection;

@Repository
@Profile("memory")
public class InMemoryKwetterRepository implements IInMemoryKwetterRepository {

    @Override
    public List<Kwetter> findAllByOwnerId(UUID ownerId) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getOwner().getId().equals(ownerId)).collect(Collectors.toList());
    }

    @Override
    public List<Kwetter> findAllByTextContains(String text) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getText().contains(text)).collect(Collectors.toList());
    }

    @Override
    public <S extends Kwetter> S save(S s) {
        kwetterCollection().removeIf(kwetter -> kwetter.getId().equals(s.getId()));
        kwetterCollection().add(s);
        Optional<Kwetter> first = kwetterCollection().stream().filter(kwetter -> kwetter.getId().equals(s.getId())).findFirst();
        return (S) first.orElse(null);
    }

    @Override
    public <S extends Kwetter> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Kwetter> findById(UUID id) {
        return kwetterCollection().stream().filter(kwetter -> kwetter.getId().equals(id)).findFirst();
    }

    @Override
    public boolean existsById(UUID aLong) {
        return false;
    }

    @Override
    public Iterable<Kwetter> findAll() {
        return new ArrayList<>(kwetterCollection());
    }

    @Override
    public Iterable<Kwetter> findAllById(Iterable<UUID> iterable) {
        return null;
    }

    @Override
    public long count() {
        return kwetterCollection().size();
    }

    /**
     * Not implemented because it isn't used.
     *
     */
    @Override
    public void deleteById(UUID aLong) {
    }

    @Override
    public void delete(Kwetter kwetter) {
        kwetterCollection().remove(kwetter);
    }

    /**
     * Not implemented because it isn't used.
     *
     */
    @Override
    public void deleteAll(Iterable<? extends Kwetter> iterable) {
    }

    @Override
    public void deleteAll() {
        kwetterCollection().clear();
    }


}
