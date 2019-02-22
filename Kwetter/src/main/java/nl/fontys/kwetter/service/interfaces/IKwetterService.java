package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

import java.util.List;

public interface IKwetterService {
    Kwetter searchForKwetter(String searchTerm);

    Kwetter createKwetter(User user, Kwetter kwetter);

    void removeKwetter(User user, Kwetter kwetter);

    void heartKwetter(User user, Kwetter kwetter);

    void removeHeartKwetter(User user, Kwetter kwetter);

    void reportKwetter(User user, Kwetter kwetter);

    void removeReportKwetter(User user, Kwetter kwetter);

    List<Kwetter> getMentionedKwetters(User user);

    List<Kwetter> getMostRecentKwetters(User user);

    List<Kwetter> getHeartedKwetters(User user);
}
