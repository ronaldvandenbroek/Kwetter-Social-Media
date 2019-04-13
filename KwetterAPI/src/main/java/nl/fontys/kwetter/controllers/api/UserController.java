package nl.fontys.kwetter.controllers.api;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesNotExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.ILoginService;
import nl.fontys.kwetter.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final IProfileService profileService;

    private final ILoginService loginService;

    @Autowired
    public UserController(IProfileService profileService, ILoginService loginService) {
        this.profileService = profileService;
        this.loginService = loginService;
    }

    @PostMapping("/update_body")
    public ResponseEntity<User> updateUser(User user) throws UserDoesNotExist, InvalidModelException, CannotLoginException {
        User login = loginService.autoLogin();
        if (user.getId() != login.getId()) {
            throw new AccessDeniedException("Trying to update a different user");
        }
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/update_name")
    public ResponseEntity<User> updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesNotExist, CannotLoginException {
        User login = loginService.autoLogin();
        if (user.getId() != login.getId()) {
            throw new AccessDeniedException("Trying to update a different user");
        }
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
