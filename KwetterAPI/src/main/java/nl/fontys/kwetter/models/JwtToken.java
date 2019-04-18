package nl.fontys.kwetter.models;

import lombok.Data;

@Data
public class JwtToken {
    private String token;

    public JwtToken(String token){
        this.token = token;
    }
}
