package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.exceptions.UsernameAlreadyExists;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final IProfileService profileService;

    @Autowired
    public UserController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/updateUser")
    public User updateUser(User user) throws UserDoesntExist, InvalidModelException {
        return profileService.updateUser(user);
    }

    @PostMapping("/updateName")
    public User updateName(User user) throws UsernameAlreadyExists, InvalidModelException, UserDoesntExist {
        return profileService.updateName(user);
    }

    @GetMapping("/getFollowers/{id}")
    public List<User> getFollowers(@PathVariable Long id) throws UserDoesntExist {
        return profileService.getFollowers(id);
    }

    @GetMapping("/getFollowing/{id}")
    public List<User> getFollowing(@PathVariable Long id) throws UserDoesntExist {
        return profileService.getFollowing(id);
    }

    @GetMapping("/getProfile/{id}")
    public User getProfile(@PathVariable Long id) throws UserDoesntExist {
        return profileService.getFullProfile(id);
    }
}
