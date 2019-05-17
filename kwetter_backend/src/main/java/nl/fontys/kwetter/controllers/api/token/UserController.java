package nl.fontys.kwetter.controllers.api.token;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExistsException;
import nl.fontys.kwetter.models.UUIDRequest;
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

@RestController("tokenUserController")
@RequestMapping(path = "/api/token/secure/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IProfileService profileService;

    private final ILoginService loginService;

    @Autowired
    public UserController(IProfileService profileService, ILoginService loginService) {
        this.profileService = profileService;
        this.loginService = loginService;
    }

    @PostMapping("/update_body")
    public ResponseEntity<User> updateUser(User user) throws ModelNotFoundException, ModelInvalidException, LoginException {
        User login = loginService.autoLogin();
        if (user.getId() != login.getId()) {
            throw new AccessDeniedException("Trying to update a different user");
        }
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/update_name")
    public ResponseEntity<User> updateName(User user) throws UsernameAlreadyExistsException, ModelInvalidException, ModelNotFoundException, LoginException {
        User login = loginService.autoLogin();
        if (user.getId() != login.getId()) {
            throw new AccessDeniedException("Trying to update a different user");
        }
        User updatedUser = profileService.updateName(user);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/follow/{id}")
    public ResponseEntity<User> follow(@PathVariable UUID id, @RequestBody User follow) throws ModelNotFoundException {
        System.out.println("Received follow request: " + id + " " + follow.getId());
        profileService.followUser(id, follow.getId());
        return ResponseEntity.ok(follow);
    }

    @PostMapping("/unfollow/{id}")
    public ResponseEntity<User> unfollow(@PathVariable UUID id, @RequestBody User unfollow) throws ModelNotFoundException {
        System.out.println("Received unfollow request: " + id + " " + unfollow.getId());
        profileService.unFollowUser(id, unfollow.getId());
        return ResponseEntity.ok(unfollow);
    }

    @GetMapping("/followers/{id}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable UUID id) throws ModelNotFoundException {
        List<User> followers = profileService.getFollowers(id);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following/{id}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable UUID id) throws ModelNotFoundException {
        List<User> following = profileService.getFollowing(id);
        return ResponseEntity.ok(following);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable UUID id) throws ModelNotFoundException {
        User user = profileService.getFullProfile(id);
        return ResponseEntity.ok(user);
    }
}
