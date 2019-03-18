package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.CouldNotDelete;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesNotExist;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.UUIDRequest;
import nl.fontys.kwetter.service.IKwetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "kwetter", produces = MediaType.APPLICATION_JSON_VALUE)
public class KwetterController {

    private final IKwetterService kwetterService;

    @Autowired
    public KwetterController(IKwetterService kwetterService) {
        this.kwetterService = kwetterService;
    }

    @PostMapping("/search_for")
    public ResponseEntity<List<Kwetter>> searchForKwetter(@RequestBody String searchTerm) {
        List<Kwetter> kwetter = kwetterService.searchForKwetter(searchTerm);
        return ResponseEntity.ok(kwetter);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Kwetter> createKwetter(@PathVariable UUID id, @RequestBody Kwetter kwetter) throws UserDoesNotExist, InvalidModelException {
        Kwetter createdKwetter = kwetterService.createKwetter(id, kwetter);
        return ResponseEntity.ok(createdKwetter);
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity removeKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws KwetterDoesNotExist, UserDoesNotExist, CouldNotDelete {
        kwetterService.removeKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed kwetter");
    }

    @PostMapping("/heart/{id}")
    public ResponseEntity heartKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws KwetterDoesNotExist, UserDoesNotExist {
        kwetterService.heartKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Hearted kwetter");
    }

    @PostMapping("/remove_heart/{id}")
    public ResponseEntity removeHeartKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws KwetterDoesNotExist, UserDoesNotExist {
        kwetterService.removeHeartKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed Heart from kwetter");
    }

    @PostMapping("/report/{id}")
    public ResponseEntity reportKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws KwetterDoesNotExist, UserDoesNotExist {
        kwetterService.reportKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Report kwetter");
    }

    @PostMapping("/remove_report/{id}")
    public ResponseEntity removeReportKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws KwetterDoesNotExist, UserDoesNotExist {
        kwetterService.removeReportKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed Report from kwetter");
    }

    @GetMapping("/mentioned/{id}")
    public ResponseEntity<List<Kwetter>> getMentionedKwetters(@PathVariable UUID id) throws UserDoesNotExist {
        List<Kwetter> mentionedKwetters = kwetterService.getMentionedKwetters(id);
        return ResponseEntity.ok(mentionedKwetters);
    }

    @GetMapping("/most_recent/{id}")
    public ResponseEntity<List<Kwetter>> getMostRecentKwetters(@PathVariable UUID id) throws UserDoesNotExist {
        List<Kwetter> recentKwetters = kwetterService.getMostRecentKwetters(id);
        return ResponseEntity.ok(recentKwetters);
    }

    @GetMapping("/hearted/{id}")
    public ResponseEntity<List<Kwetter>> getHeartedKwetters(@PathVariable UUID id) throws UserDoesNotExist {
        List<Kwetter> heartedKwetters = kwetterService.getHeartedKwetters(id);
        return ResponseEntity.ok(heartedKwetters);
    }

    @GetMapping("/timeline/{id}")
    public ResponseEntity<List<Kwetter>> getTimeLine(@PathVariable UUID id) throws UserDoesNotExist {
        List<Kwetter> timelineKwetters = kwetterService.getTimeline(id);
        return ResponseEntity.ok(timelineKwetters);
    }
}
