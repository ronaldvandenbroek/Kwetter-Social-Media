package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IKwetterService;

import java.util.List;

public class KwetterService implements IKwetterService {
    @Override
    public Kwetter searchForKwetter(String searchTerm) {
        return null;
    }

    @Override
    public Kwetter createKwetter(User user, Kwetter kwetter) {
        return null;
    }

    @Override
    public void removeKwetter(User user, Kwetter kwetter) {

    }

    @Override
    public void heartKwetter(User user, Kwetter kwetter) {

    }

    @Override
    public void removeHeartKwetter(User user, Kwetter kwetter) {

    }

    @Override
    public void reportKwetter(User user, Kwetter kwetter) {

    }

    @Override
    public void removeReportKwetter(User user, Kwetter kwetter) {

    }

    @Override
    public List<Kwetter> getMentionedKwetters(User user) {
        return null;
    }

    @Override
    public List<Kwetter> getMostRecentKwetters(User user) {
        return null;
    }

    @Override
    public List<Kwetter> getHeartedKwetters(User user) {
        return null;
    }
}
