package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.LongRequest;
import nl.fontys.kwetter.service.interfaces.IKwetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KwetterController {

    private final IKwetterService kwetterService;

    @Autowired
    public KwetterController(IKwetterService kwetterService) {
        this.kwetterService = kwetterService;
    }

    @PostMapping("/searchFor")
    public ResponseEntity<Kwetter> searchForKwetter(@RequestBody String searchTerm) {
        Kwetter kwetter = kwetterService.searchForKwetter(searchTerm);
        return ResponseEntity.ok(kwetter);
    }

    @PostMapping("/createKwetter/{id}")
    public ResponseEntity<Kwetter> createKwetter(@PathVariable Long id, @RequestBody Kwetter kwetter) throws UserDoesntExist, InvalidModelException {
        Kwetter createdKwetter = kwetterService.createKwetter(id, kwetter);
        return ResponseEntity.ok(createdKwetter);
    }

    @PostMapping("/removeKwetter/{id}")
    public ResponseEntity removeKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeKwetter(id, longRequest.getId());
        return ResponseEntity.ok("Removed kwetter");
    }

    @PostMapping("/heartKwetter/{id}")
    public ResponseEntity heartKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.heartKwetter(id, longRequest.getId());
        return ResponseEntity.ok("Hearted kwetter");
    }

    @PostMapping("/removeHeartKwetter/{id}")
    public ResponseEntity removeHeartKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeHeartKwetter(id, longRequest.getId());
        return ResponseEntity.ok("Removed Heart from kwetter");
    }

    @PostMapping("/reportKwetter/{id}")
    public ResponseEntity reportKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.reportKwetter(id, longRequest.getId());
        return ResponseEntity.ok("Report kwetter");
    }

    @PostMapping("/removeReportKwetter/{id}")
    public ResponseEntity removeReportKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeReportKwetter(id, longRequest.getId());
        return ResponseEntity.ok("Removed Report from kwetter");
    }

    @GetMapping("/getMentionedKwetters/{id}")
    public ResponseEntity<List<Kwetter>> getMentionedKwetters(@PathVariable Long id) throws UserDoesntExist{
        List<Kwetter> mentionedKwetters = kwetterService.getMentionedKwetters(id);
        return ResponseEntity.ok(mentionedKwetters);
    }

    @GetMapping("/getMostRecentKwetters/{id}")
    public ResponseEntity<List<Kwetter>> getMostRecentKwetters(@PathVariable Long id) throws UserDoesntExist{
        List<Kwetter> recentKwetters = kwetterService.getMostRecentKwetters(id);
        return ResponseEntity.ok(recentKwetters);
    }

    @GetMapping("/getHeartedKwetters/{id}")
    public ResponseEntity<List<Kwetter>> getHeartedKwetters(@PathVariable Long id) throws UserDoesntExist{
        List<Kwetter> heartedKwetters = kwetterService.getHeartedKwetters(id);
        return ResponseEntity.ok(heartedKwetters);
    }
}
