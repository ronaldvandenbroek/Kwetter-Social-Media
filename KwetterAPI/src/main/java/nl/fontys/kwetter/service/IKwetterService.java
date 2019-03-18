package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.CouldNotDelete;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesNotExist;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Kwetter;

import java.util.List;
import java.util.UUID;

public interface IKwetterService {
    List<Kwetter> searchForKwetter(String searchTerm);

    Kwetter createKwetter(UUID userId, Kwetter kwetter) throws UserDoesNotExist, InvalidModelException;

    void removeKwetter(UUID userId, UUID kwetterId) throws KwetterDoesNotExist, UserDoesNotExist, CouldNotDelete;

    void heartKwetter(UUID userId, UUID kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void removeHeartKwetter(UUID userId, UUID kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void reportKwetter(UUID userId, UUID kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    void removeReportKwetter(UUID userId, UUID kwetterId) throws KwetterDoesNotExist, UserDoesNotExist;

    List<Kwetter> getMentionedKwetters(UUID userId) throws UserDoesNotExist;

    List<Kwetter> getMostRecentKwetters(UUID userId) throws UserDoesNotExist;

    List<Kwetter> getTimeline(UUID userId) throws UserDoesNotExist;

    List<Kwetter> getHeartedKwetters(UUID userId) throws UserDoesNotExist;
}
