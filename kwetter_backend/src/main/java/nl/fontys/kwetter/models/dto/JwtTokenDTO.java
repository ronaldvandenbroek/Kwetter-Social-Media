package nl.fontys.kwetter.models.dto;

import lombok.Data;

@Data
public class JwtTokenDTO {
    private String token;
    private UserDTO user;

    public JwtTokenDTO(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
}
