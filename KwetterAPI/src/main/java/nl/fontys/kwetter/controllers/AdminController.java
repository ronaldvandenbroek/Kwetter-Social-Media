package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.UserDoesntExist;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    private final IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/changeRole")
    public void login(@RequestBody User user) throws UserDoesntExist {
        adminService.changeRole(user.getId(), user.getRole());
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
}
