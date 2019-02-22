package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;

public interface KwetterDao {
    Kwetter createKwetter(User user, Kwetter kwetter);

    void removeKwetter(User user, Kwetter kwetter);

    void heartKwetter(User user, Kwetter kwetter);

    void removeHeartKwetter(User user, Kwetter kwetter);

    void reportKwetter(User user, Kwetter kwetter);

    void removeReportKwetter(User user, Kwetter kwetter);
}
