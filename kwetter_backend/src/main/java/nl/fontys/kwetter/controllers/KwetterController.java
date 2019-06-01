package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.service.IHateoasService;
import nl.fontys.kwetter.service.IKwetterService;
import nl.fontys.kwetter.service.IWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/secure/", produces = MediaType.APPLICATION_JSON_VALUE)
public class KwetterController {

    private final IKwetterService kwetterService;
    private final IHateoasService hateoasService;
    private final IWebSocketService webSocketService;

    @Autowired
    public KwetterController(IKwetterService kwetterService, IHateoasService hateoasService, IWebSocketService webSocketService) {
        this.kwetterService = kwetterService;
        this.hateoasService = hateoasService;
        this.webSocketService = webSocketService;
    }

    @PostMapping("kwetter/search_for")
    public ResponseEntity<List<KwetterDTO>> searchForKwetter(@RequestBody String searchTerm) {
        List<Kwetter> kwetters = kwetterService.searchForKwetter(searchTerm);
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(kwetters));
    }

    @PostMapping("user/{userId}/create")
    public ResponseEntity<KwetterDTO> createKwetter(@PathVariable UUID userId, @RequestBody KwetterDTO kwetter) {
        Kwetter createdKwetter = kwetterService.createKwetter(userId, kwetter);
        webSocketService.sendTimelineUpdate("A new kwetter has been created");
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(createdKwetter));
    }

    @GetMapping("user/{userId}/kwetter/{kwetterId}/remove")
    public ResponseEntity removeKwetter(@PathVariable UUID userId, @PathVariable UUID kwetterId) {
        kwetterService.removeKwetter(userId, kwetterId);
        return ResponseEntity.ok("Removed kwetter");
    }

    @GetMapping("user/{userId}/kwetter/{kwetterId}/heart")
    public ResponseEntity heartKwetter(@PathVariable UUID userId, @PathVariable UUID kwetterId) {
        kwetterService.heartKwetter(userId, kwetterId);
        return ResponseEntity.ok("Hearted kwetter");
    }

    @GetMapping("user/{userId}/kwetter/{kwetterId}/remove_heart")
    public ResponseEntity removeHeartKwetter(@PathVariable UUID userId, @PathVariable UUID kwetterId) {
        kwetterService.removeHeartKwetter(userId, kwetterId);
        return ResponseEntity.ok("Removed Heart from kwetter");
    }

    @GetMapping("user/{userId}/kwetter/{kwetterId}/report")
    public ResponseEntity reportKwetter(@PathVariable UUID userId, @PathVariable UUID kwetterId) {
        kwetterService.reportKwetter(userId, kwetterId);
        return ResponseEntity.ok("Report kwetter");
    }

    @GetMapping("user/{userId}/kwetter/{kwetterId}/remove_report")
    public ResponseEntity removeReportKwetter(@PathVariable UUID userId, @PathVariable UUID kwetterId) {
        kwetterService.removeReportKwetter(userId, kwetterId);
        return ResponseEntity.ok("Removed Report from kwetter");
    }

    @GetMapping("user/{userId}/mentioned")
    public ResponseEntity<List<KwetterDTO>> getMentionedKwetters(@PathVariable UUID userId) {
        List<Kwetter> mentionedKwetters = kwetterService.getMentionedKwetters(userId);
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(mentionedKwetters));
    }

    @GetMapping("user/{userId}/most_recent")
    public ResponseEntity<List<KwetterDTO>> getMostRecentKwetters(@PathVariable UUID userId) {
        List<Kwetter> recentKwetters = kwetterService.getMostRecentKwetters(userId);
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(recentKwetters));
    }

    @GetMapping("user/{userId}/hearted")
    public ResponseEntity<List<KwetterDTO>> getHeartedKwetters(@PathVariable UUID userId) {
        List<Kwetter> heartedKwetters = kwetterService.getHeartedKwetters(userId);
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(heartedKwetters));
    }

    @GetMapping("user/{userId}/timeline")
    public ResponseEntity<List<KwetterDTO>> getTimeline(@PathVariable UUID userId) {
        List<Kwetter> timelineKwetters = kwetterService.getTimeline(userId);
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(timelineKwetters));
    }
}
