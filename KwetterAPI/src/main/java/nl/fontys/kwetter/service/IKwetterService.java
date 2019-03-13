package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesNotExist;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Kwetter;

import java.util.List;

public interface IKwetterService {
    Kwetter searchForKwetter(String searchTerm);

    Kwetter createKwetter(Long userId, Kwetter kwetter) throws UserDoesNotExist, InvalidModelException;

    void removeKwetter(Long userId, Long kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void heartKwetter(Long userId, Long kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void removeHeartKwetter(Long userId, Long kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void reportKwetter(Long userId, Long kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void removeReportKwetter(Long userId, Long kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    List<Kwetter> getMentionedKwetters(Long userId) throws UserDoesNotExist;

    List<Kwetter> getMostRecentKwetters(Long userId) throws UserDoesNotExist;

    List<Kwetter> getTimeline(Long userId) throws UserDoesNotExist;

    List<Kwetter> getHeartedKwetters(Long userId) throws UserDoesNotExist;
}
