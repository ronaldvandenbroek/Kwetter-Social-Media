package nl.fontys.kwetter.service.implementation;

import nl.fontys.kwetter.service.IWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class WebSocketService implements IWebSocketService {

    private final SimpMessagingTemplate template;

    @Autowired
    WebSocketService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendTimelineUpdate(String message) {
        this.template.convertAndSend("/timeline", new SimpleDateFormat("HH:mm:ss").format(new Date()) + "- " + message);
    }
}
