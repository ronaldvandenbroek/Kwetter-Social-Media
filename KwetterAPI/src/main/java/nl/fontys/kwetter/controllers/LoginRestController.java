package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.interfaces.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginRestController {

    private final ILoginService loginService;

    @Autowired
    public LoginRestController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public User login(@RequestBody String email, String password) throws InvalidModelException, CannotLoginException {
        return loginService.login(email, password);
    }

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
