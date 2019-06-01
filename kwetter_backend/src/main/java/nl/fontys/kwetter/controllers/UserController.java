package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.service.IHateoasService;
import nl.fontys.kwetter.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/secure/user/", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final IProfileService profileService;
    private final IHateoasService hateoasService;

    @Autowired
    public UserController(IProfileService profileService, IHateoasService hateoasService) {
        this.hateoasService = hateoasService;
        this.profileService = profileService;
    }

    @GetMapping("{userId}/followers")
    public ResponseEntity<List<UserDTO>> getFollowers(@PathVariable UUID userId) {
        List<User> followers = profileService.getFollowers(userId);

        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(followers));
    }

    @GetMapping("{userId}/following")
    public ResponseEntity<List<UserDTO>> getFollowing(@PathVariable UUID userId) {
        List<User> following = profileService.getFollowing(userId);
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(following));
    }

    @GetMapping("{userId}/profile")
    public ResponseEntity<UserDTO> getProfile(@PathVariable UUID userId) {
        User user = profileService.getFullProfile(userId);
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(user));
    }

    @PostMapping("{userId}/follow")
    public ResponseEntity<UserDTO> follow(@PathVariable UUID userId, @RequestBody UserDTO follow) {
        User followUser = profileService.getFullProfile(follow.getUuid());
        profileService.followUser(userId, follow.getUuid());
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(followUser));
    }

    @PostMapping("{userId}/unfollow")
    public ResponseEntity<UserDTO> unfollow(@PathVariable UUID userId, @RequestBody UserDTO unfollow) {
        User unFollowUser = profileService.getFullProfile(unfollow.getUuid());
        profileService.unFollowUser(userId, unfollow.getUuid());
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(unFollowUser));
    }

    @PostMapping("update_body")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user) {
        User updatedUser = profileService.updateUser(user);
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(updatedUser));
    }

    @PostMapping("update_name")
    public ResponseEntity<UserDTO> updateName(@RequestBody UserDTO user) {
        User updatedUser = profileService.updateName(user);
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(updatedUser));
    }

    @GetMapping("verify/{uuid}")
    public ResponseEntity verify(@PathVariable UUID uuid) {
        profileService.verify(uuid);
        return ResponseEntity.ok().build();
    }
}
