package nl.fontys.kwetter.service;

import nl.fontys.kwetter.exceptions.CouldNotDeleteModelException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.NotImplementedException;
import nl.fontys.kwetter.models.Kwetter;

import java.util.List;
import java.util.UUID;

public interface IKwetterService {
    List<Kwetter> searchForKwetter(String searchTerm);

    Kwetter createKwetter(UUID userId, Kwetter kwetter) throws ModelNotFoundException, ModelInvalidException;

    void removeKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException, CouldNotDeleteModelException;

    void heartKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException;

    void removeHeartKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException;

    void reportKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException;

    void removeReportKwetter(UUID userId, UUID kwetterId) throws ModelNotFoundException;

    List<Kwetter> getMentionedKwetters(UUID userId) throws ModelNotFoundException, NotImplementedException;

    List<Kwetter> getMostRecentKwetters(UUID userId) throws ModelNotFoundException;

    List<Kwetter> getTimeline(UUID userId) throws ModelNotFoundException;

    List<Kwetter> getHeartedKwetters(UUID userId) throws ModelNotFoundException;
}
