package nl.fontys.kwetter.controllers.api.token;

import nl.fontys.kwetter.exceptions.CouldNotDeleteModelException;
import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.UUIDRequest;
import nl.fontys.kwetter.service.IKwetterService;
import nl.fontys.kwetter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("tokenKwetterController")
@RequestMapping(path = "/api/token/secure/kwetter", produces = MediaType.APPLICATION_JSON_VALUE)
public class KwetterController {

    private final IKwetterService kwetterService;

    private final ILoginService loginService;

    @Autowired
    public KwetterController(IKwetterService kwetterService, ILoginService loginService) {
        this.kwetterService = kwetterService;
        this.loginService = loginService;
    }

    @PostMapping("/remove/{id}")
    public ResponseEntity removeKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws ModelNotFoundException, CouldNotDeleteModelException {
        kwetterService.removeKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed kwetter");
    }

    @PostMapping("/search_for")
    public ResponseEntity<List<Kwetter>> searchForKwetter(@RequestBody String searchTerm) {
        List<Kwetter> kwetter = kwetterService.searchForKwetter(searchTerm);
        return ResponseEntity.ok(kwetter);
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<Kwetter> createKwetter(@PathVariable UUID id, @RequestBody Kwetter kwetter) throws ModelNotFoundException, ModelInvalidException {
        Kwetter createdKwetter = kwetterService.createKwetter(id, kwetter);
        return ResponseEntity.ok(createdKwetter);
    }

    @PostMapping("/heart/{id}")
    public ResponseEntity heartKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws ModelNotFoundException {
        kwetterService.heartKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Hearted kwetter");
    }

    @PostMapping("/remove_heart/{id}")
    public ResponseEntity removeHeartKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws ModelNotFoundException {
        kwetterService.removeHeartKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed Heart from kwetter");
    }

    @PostMapping("/report/{id}")
    public ResponseEntity reportKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws ModelNotFoundException {
        kwetterService.reportKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Report kwetter");
    }

    @PostMapping("/remove_report/{id}")
    public ResponseEntity removeReportKwetter(@PathVariable UUID id, @RequestBody UUIDRequest UUIDRequest) throws ModelNotFoundException {
        kwetterService.removeReportKwetter(id, UUIDRequest.getId());
        return ResponseEntity.ok("Removed Report from kwetter");
    }

    @GetMapping("/mentioned/{id}")
    public ResponseEntity<List<Kwetter>> getMentionedKwetters(@PathVariable UUID id) throws ModelNotFoundException {
        List<Kwetter> mentionedKwetters = kwetterService.getMentionedKwetters(id);
        return ResponseEntity.ok(mentionedKwetters);
    }

    @GetMapping("/most_recent/{id}")
    public ResponseEntity<List<Kwetter>> getMostRecentKwetters(@PathVariable UUID id) throws ModelNotFoundException {
        List<Kwetter> recentKwetters = kwetterService.getMostRecentKwetters(id);
        return ResponseEntity.ok(recentKwetters);
    }

    @GetMapping("/hearted/{id}")
    public ResponseEntity<List<Kwetter>> getHeartedKwetters(@PathVariable UUID id) throws ModelNotFoundException {
        List<Kwetter> heartedKwetters = kwetterService.getHeartedKwetters(id);
        return ResponseEntity.ok(heartedKwetters);
    }

    @GetMapping("/timeline/{id}")
    public ResponseEntity<List<Kwetter>> getTimeLine(@PathVariable UUID id) throws ModelNotFoundException {
        List<Kwetter> timelineKwetters = kwetterService.getTimeline(id);
        return ResponseEntity.ok(timelineKwetters);
    }
}
