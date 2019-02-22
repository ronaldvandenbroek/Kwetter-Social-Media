package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.Kwetter;

public interface KwetterDao {
    boolean createNewKwetter(Kwetter kwetter);

    boolean updateKwetter(Kwetter kwetter);

    boolean deleteKwetter(Kwetter kwetter);
}
