package nl.fontys.kwetter.dao.memory;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IKwetterDao {
    boolean createNewKwetter(Kwetter kwetter);

    boolean updateKwetter(Kwetter kwetter);

    boolean deleteKwetter(Kwetter kwetter);

    List<Kwetter> getAllKwetters();

    List<Kwetter> getAllCreatedKwettersFromUser(User user);

    List<Kwetter> getAllReportedKwettersFromUser(User user);

    List<Kwetter> getAllHeartedKwettersFromUser(User user);

    Kwetter getKwetterById(Long kwetterId);
}
