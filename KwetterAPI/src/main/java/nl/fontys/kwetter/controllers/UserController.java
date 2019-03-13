package nl.fontys.kwetter.controllers;

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

@RestController
@RequestMapping(path = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IProfileService profileService;

    @Autowired
    public UserController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(User user) throws UserDoesNotExist, InvalidModelException {
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/updateName")
    public ResponseEntity<User> updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesNotExist {
        User updatedUser = profileService.updateName(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/getFollowers/{id}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long id) throws UserDoesNotExist {
        List<User> followers = profileService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/getFollowing/{id}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) throws UserDoesNotExist {
        List<User> following = profileService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/getProfile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) throws UserDoesNotExist {
        User user = profileService.getFullProfile(id);
        return ResponseEntity.ok(user);
    }
}
