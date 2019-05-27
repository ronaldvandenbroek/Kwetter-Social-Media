package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.entity.Kwetter;

import java.util.List;
import java.util.UUID;

public interface IKwetterService {
    List<Kwetter> searchForKwetter(String searchTerm);

    Kwetter createKwetter(UUID userId, KwetterDTO kwetter);

    void removeKwetter(UUID userId, UUID kwetterId);

    void heartKwetter(UUID userId, UUID kwetterId);

    void removeHeartKwetter(UUID userId, UUID kwetterId);

    void reportKwetter(UUID userId, UUID kwetterId);

    void removeReportKwetter(UUID userId, UUID kwetterId);

    List<Kwetter> getMentionedKwetters(UUID userId);

    List<Kwetter> getMostRecentKwetters(UUID userId);

    List<Kwetter> getTimeline(UUID userId);

    List<Kwetter> getHeartedKwetters(UUID userId);
}
