package nl.fontys.kwetter.controllers.api.token;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.repository.memory.implementation.data.InMemoryDatabase;
import nl.fontys.kwetter.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("tokenUnsecureAdminController")
@RequestMapping(path = "/api/token/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class UnsecureAdminController {

    private final IAdminService adminService;

    @Autowired
    public UnsecureAdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/change_role")
    public ResponseEntity changeRole(@RequestBody Credentials credentials) throws ModelNotFoundException {
        adminService.changeRole(credentials.getEmail(), credentials.getRole());
        return ResponseEntity.ok("Changed user role");
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/get_all_kwetters")
    public ResponseEntity<List<Kwetter>> getAllKwetters() {
        List<Kwetter> allKwetters = adminService.getAllKwetters();
        return ResponseEntity.ok(allKwetters);
    }

    @GetMapping("/in_memory_users")
    public ResponseEntity<List<User>> getInMemoryUsers() {
        List<User> allUsers = new ArrayList<>(InMemoryDatabase.userCollection());
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/test_fail")
    public ResponseEntity<String> failTest() throws LoginException {
        throw new LoginException("Exception test");
    }
}
