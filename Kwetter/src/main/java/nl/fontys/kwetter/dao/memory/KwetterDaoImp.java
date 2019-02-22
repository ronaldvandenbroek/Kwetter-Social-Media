package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.dao.KwetterDao;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Stateless
public class KwetterDaoImp implements KwetterDao {

    @Override
    public boolean createNewKwetter(Kwetter kwetter) {
        if (InMemoryCollection.getAllKwetters().contains(kwetter)) {
            return false;
        } else {
            kwetter.setId(InMemoryCollection.getNextFreeKwetterID());
            InMemoryCollection.getAllKwetters().add(kwetter);
            return true;
        }
    }

    @Override
    public boolean updateKwetter(Kwetter kwetter) {
        if (InMemoryCollection.getAllKwetters().contains(kwetter)) {
            for (Kwetter oldKwetter : InMemoryCollection.getAllKwetters()) {
                if (oldKwetter.equals(kwetter)) {
                    oldKwetter = kwetter;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteKwetter(Kwetter kwetter) {
        Collection<Kwetter> allKwetters = InMemoryCollection.getAllKwetters();
        if (allKwetters.contains(kwetter)) {
            for (Kwetter oldKwetter : allKwetters) {
                if (oldKwetter.equals(kwetter)) {
                    kwetter.setOwner(null);
                    allKwetters.remove(oldKwetter);
                    allKwetters.add(kwetter);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<Kwetter> getAllKwetters() {
        return (List<Kwetter>) InMemoryCollection.getAllKwetters();
    }

    @Override
    public List<Kwetter> getAllCreatedKwettersFromUser(User user) {
        if (InMemoryCollection.getAllUsers().contains(user)) {
            Collection<User> users = InMemoryCollection.getAllUsers();
            for (User allUser : InMemoryCollection.getAllUsers()
            ) {
                if (allUser.equals(user)) {
                    return new ArrayList<>(allUser.getCreatedKwetters());
                }
            }
        }
        return null;
    }

    @Override
    public List<Kwetter> getAllReportedKwettersFromUser(User user) {
        if (InMemoryCollection.getAllUsers().contains(user)) {
            for (User allUser : InMemoryCollection.getAllUsers()
            ) {
                if (allUser.equals(user)) {
                    return new ArrayList<>(allUser.getReportedKwetters());
                }
            }
        }
        return null;
    }

    @Override
    public List<Kwetter> getAllHeartedKwettersFromUser(User user) {
        if (InMemoryCollection.getAllUsers().contains(user)) {
            for (User allUser : InMemoryCollection.getAllUsers()
            ) {
                if (allUser.equals(user)) {
                    return new ArrayList<>(allUser.getHeartedKwetters());
                }
            }
        }
        return null;
    }

    @Override
    public Kwetter getKwetterById(Long kwetterId) {
        for (Kwetter kwetter : InMemoryCollection.getAllKwetters()) {
            if (kwetter.getId().equals(kwetterId)) {
                return new Kwetter(kwetter);
            }
        }
        return null;
    }
}
