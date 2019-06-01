package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.service.IProfileService;
import nl.fontys.kwetter.service.IWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WebSocketService implements IWebSocketService {

    private final SimpMessagingTemplate template;
    private final IProfileService profileService;

    @Autowired
    WebSocketService(SimpMessagingTemplate template, IProfileService profileService) {
        this.template = template;
        this.profileService = profileService;
    }

    public void sendTimelineUpdateToFollowers(UUID senderUuid, Kwetter createdKwetter) {
        User sender = profileService.getFullProfile(senderUuid);
        List<User> followers = profileService.getFollowers(senderUuid);
        for (User follower : followers) {
            this.template.convertAndSend("/timeline/" + follower.getId().toString(), sender.getName() + " posted " + createdKwetter.getText());
        }
    }
}
