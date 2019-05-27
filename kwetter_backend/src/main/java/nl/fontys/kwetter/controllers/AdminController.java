package nl.fontys.kwetter.controllers;

import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.service.IAdminService;
import nl.fontys.kwetter.service.IHateoasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final IAdminService adminService;
    private final IHateoasService hateoasService;

    @Autowired
    public AdminController(IAdminService adminService, IHateoasService hateoasService) {
        this.adminService = adminService;
        this.hateoasService = hateoasService;
    }

    @GetMapping("/get_all_users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(hateoasService.getUserDTOWithLinks(allUsers));
    }

    @GetMapping("/get_all_kwetters")
    public ResponseEntity<List<KwetterDTO>> getAllKwetters() {
        List<Kwetter> allKwetters = adminService.getAllKwetters();
        return ResponseEntity.ok(hateoasService.getKwetterDTOWithLinks(allKwetters));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test");
    }

    @GetMapping("/test_fail")
    public ResponseEntity<String> failTest() {
        throw new LoginException("Exception test");
    }
}
