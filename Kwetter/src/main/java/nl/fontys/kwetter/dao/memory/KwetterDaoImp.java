package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.dao.KwetterDao;
import nl.fontys.kwetter.dao.memory.data.InMemoryCollection;
import nl.fontys.kwetter.models.Kwetter;

public class KwetterDaoImp implements KwetterDao {

    @Override
    public boolean createNewKwetter(Kwetter kwetter) {

        if (InMemoryCollection.getAllKwetters().contains(kwetter)){
            return false;
        }
        else {
            InMemoryCollection.getAllKwetters().add(kwetter);
            return true;
        }
    }

    @Override
    public boolean updateKwetter(Kwetter kwetter) {
        if (InMemoryCollection.getAllKwetters().contains(kwetter)){
            for (Kwetter oldKwetter : InMemoryCollection.getAllKwetters()) {
                if (oldKwetter.equals(kwetter)){
                    oldKwetter = kwetter;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean deleteKwetter(Kwetter kwetter) {
        if (InMemoryCollection.getAllKwetters().contains(kwetter)){
            for (Kwetter oldKwetter : InMemoryCollection.getAllKwetters()) {
                if (oldKwetter.equals(kwetter)){
                    oldKwetter.setOwner(null);
                    return true;
                }
            }
        }
        return false;
    }
}
