package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.KwetterDoesntExist;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.LongRequest;
import nl.fontys.kwetter.service.interfaces.IKwetterService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Kwetter searchForKwetter(@RequestBody String searchTerm) {
        return kwetterService.searchForKwetter(searchTerm);
    }

    @PostMapping("/createKwetter/{id}")
    public Kwetter createKwetter(@PathVariable Long id, @RequestBody Kwetter kwetter) throws UserDoesntExist, InvalidModelException {
        return kwetterService.createKwetter(id, kwetter);
    }

    @PostMapping("/removeKwetter/{id}")
    public void removeKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeKwetter(id, longRequest.getId());
    }

    @PostMapping("/heartKwetter/{id}")
    public void heartKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.heartKwetter(id, longRequest.getId());
    }

    @PostMapping("/removeHeartKwetter/{id}")
    public void removeHeartKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeHeartKwetter(id, longRequest.getId());
    }

    @PostMapping("/reportKwetter/{id}")
    public void reportKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.reportKwetter(id, longRequest.getId());
    }

    @PostMapping("/removeReportKwetter/{id}")
    public void removeReportKwetter(@PathVariable Long id, @RequestBody LongRequest longRequest) throws KwetterDoesntExist, UserDoesntExist{
        kwetterService.removeReportKwetter(id, longRequest.getId());
    }

    @GetMapping("/getMentionedKwetters/{id}")
    public List<Kwetter> getMentionedKwetters(@PathVariable Long id) throws UserDoesntExist{
        return kwetterService.getMentionedKwetters(id);
    }

    @GetMapping("/getMostRecentKwetters/{id}")
    public List<Kwetter> getMostRecentKwetters(@PathVariable Long id) throws UserDoesntExist{
        return kwetterService.getMostRecentKwetters(id);
    }

    @GetMapping("/getHeartedKwetters/{id}")
    public List<Kwetter> getHeartedKwetters(@PathVariable Long id) throws UserDoesntExist{
        return kwetterService.getHeartedKwetters(id);
    }
}
