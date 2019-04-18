package nl.fontys.kwetter.models;

import lombok.Data;

@Data
public class JwtToken {
    private String token;
    private User user;

    public JwtToken(String token, User user){
        this.token = token;
        this.user = user;
    }
}
