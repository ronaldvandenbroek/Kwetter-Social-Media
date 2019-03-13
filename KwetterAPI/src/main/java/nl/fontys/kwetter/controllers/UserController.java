package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final IProfileService profileService;

    @Autowired
    public UserController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/updateUser")
    public ResponseEntity<User> updateUser(User user) throws UserDoesntExist, InvalidModelException {
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/updateName")
    public ResponseEntity<User> updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist {
        User updatedUser = profileService.updateName(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/getFollowers/{id}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable Long id) throws UserDoesntExist {
        List<User> followers = profileService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/getFollowing/{id}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) throws UserDoesntExist {
        List<User> following = profileService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/getProfile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) throws UserDoesntExist {
        User user = profileService.getFullProfile(id);
        return ResponseEntity.ok(user);
    }
}
