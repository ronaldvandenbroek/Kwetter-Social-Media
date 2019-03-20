package nl.fontys.kwetter.controllers.jsf;

import nl.fontys.kwetter.exceptions.CannotLoginException;
import nl.fontys.kwetter.exceptions.InvalidModelException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "login", produces = MediaType.APPLICATION_JSON_VALUE)
public class JsfLoginController {

    private final ILoginService loginService;

    Credentials credentials = new Credentials();

    public void Login(){
        try {
            loginService.login(credentials);
            credentials = new Credentials();
        } catch (CannotLoginException | InvalidModelException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public JsfLoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody Credentials credentials) throws InvalidModelException, CannotLoginException {
        User user = loginService.login(credentials);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/test_fail")
    public ResponseEntity<String> failTest() throws CannotLoginException {
        throw new CannotLoginException("Exception test");
    }
}
