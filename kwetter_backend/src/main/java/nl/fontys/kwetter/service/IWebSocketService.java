package nl.fontys.kwetter.service;

import nl.fontys.kwetter.models.entity.Kwetter;

import java.util.UUID;

public interface IWebSocketService {

    void sendTimelineUpdateToFollowers(UUID senderUuid, Kwetter createdKwetter);
}
