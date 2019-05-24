package nl.fontys.kwetter.controllers.api.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.exceptions.ModelNotFoundException;
import nl.fontys.kwetter.models.JwtToken;
import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.service.ILoginService;
import nl.fontys.kwetter.service.IProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.UriComponentsBuilderMethodArgumentResolver;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@RestController("tokenLoginController")
@RequestMapping(path = "/api/token/", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    private ILoginService loginService;
    private IProfileService profileService;

    @Autowired
    public LoginController(IProfileService profileService, ILoginService loginService) {
        this.profileService = profileService;
        this.loginService = loginService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtToken> login(@RequestBody CredentialsDTO credentials) throws ModelInvalidException, LoginException {

        if (logger.isDebugEnabled()) {
            logger.info(String.format("%s is trying to login", credentials.getEmail()));
        }
        User userLoggedIn = loginService.login(credentials);

        String jwtToken = Jwts.builder().setSubject(userLoggedIn.getCredentials().getEmail())
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .compact();

        JwtToken token = new JwtToken(jwtToken, userLoggedIn);

        if (logger.isDebugEnabled()) {
            logger.info(String.format("%s logged in successfully", credentials.getEmail()));
        }
        return ResponseEntity.ok(token);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable UUID id, HttpServletRequest request) throws ModelNotFoundException {
        logger.info(request.getRequestURI());
        User user = profileService.getFullProfile(id);

        UriComponentsBuilder.fromHttpRequest(request).
        return ResponseEntity.ok(user);
    }
}
