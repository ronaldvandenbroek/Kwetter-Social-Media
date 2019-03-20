package nl.fontys.kwetter.controllers.rest;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IProfileService profileService;

    @Autowired
    public UserController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/update_body")
    public ResponseEntity<User> updateUser(User user) throws UserDoesNotExist, InvalidModelException {
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/update_name")
    public ResponseEntity<User> updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesNotExist {
        User updatedUser = profileService.updateName(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable UUID id) throws UserDoesNotExist {
        List<User> followers = profileService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable UUID id) throws UserDoesNotExist {
        List<User> following = profileService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable UUID id) throws UserDoesNotExist {
        User user = profileService.getFullProfile(id);
        return ResponseEntity.ok(user);
    }
}
