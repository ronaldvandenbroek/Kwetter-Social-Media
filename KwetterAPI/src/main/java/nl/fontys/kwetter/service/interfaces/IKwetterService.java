package nl.fontys.kwetter.service.interfaces;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;

import java.util.List;

public interface IKwetterService {
    Kwetter searchForKwetter(String searchTerm);

    Kwetter createKwetter(Long userId, Kwetter kwetter) throws UserDoesntExist, InvalidModelException;

    void removeKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist;

    void heartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist;

    void removeHeartKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist;

    void reportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist;

    void removeReportKwetter(Long userId, Long kwetterId) throws KwetterDoesntExist, UserDoesntExist;

    List<Kwetter> getMentionedKwetters(Long userId) throws UserDoesntExist;

    List<Kwetter> getMostRecentKwetters(Long userId) throws UserDoesntExist;

    List<Kwetter> getHeartedKwetters(Long userId) throws UserDoesntExist;
}
