package nl.fontys.kwetter.controllers.api.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nl.fontys.kwetter.exceptions.LoginException;
import nl.fontys.kwetter.exceptions.ModelInvalidException;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.JwtToken;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("tokenLoginController")
@RequestMapping(path = "/api/token/", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private ILoginService loginService;

    @Autowired
    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("login")
    public ResponseEntity<JwtToken> login(@RequestBody Credentials credentials) throws ModelInvalidException, LoginException {
        System.out.println(credentials.getEmail() + " is trying to log in");
        User userLoggedIn = loginService.login(credentials);

        String jwtToken = Jwts.builder().setSubject(userLoggedIn.getCredentials().getEmail())
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey")
                .compact();

        JwtToken token = new JwtToken(jwtToken, userLoggedIn);

        System.out.println(credentials.getEmail() + " logged in successfully");
        return ResponseEntity.ok(token);
    }
}
