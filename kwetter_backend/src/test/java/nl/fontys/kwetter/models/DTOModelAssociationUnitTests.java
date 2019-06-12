package nl.fontys.kwetter.models;

import nl.fontys.kwetter.models.dto.CredentialsDTO;
import nl.fontys.kwetter.models.dto.JwtTokenDTO;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.Credentials;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;


@DisplayName("Testing the associations of the models")
class DTOModelAssociationUnitTests {

    @Test
    void pipelineTest() {
        //fail("This is a controlled fail to test the pipeline");
    }

    @Test
    void createAJwtTokenDTO() {
        String token = "token";
        UserDTO user = new UserDTO();

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO(token, user);

        assertNotNull(jwtTokenDTO);
        assertNotNull(jwtTokenDTO.getUser());
        assertEquals(token, jwtTokenDTO.getToken());
    }

    @Test
    void createAKwetterDTO() {
        String text = "text";
        User owner = new User();
        Date date = new Date();

        KwetterDTO kwetterDTO = new KwetterDTO(text, owner, date);

        assertNotNull(kwetterDTO);
        assertNotNull(kwetterDTO.getOwner());
        assertEquals(text, kwetterDTO.getText());
        assertEquals(date, kwetterDTO.getDateTime());
    }

    @Test
    void cloneAKwetterDTO() {
        Kwetter kwetter = new Kwetter();

        KwetterDTO kwetterDTO = new KwetterDTO(kwetter);

        assertNotNull(kwetterDTO);
        assertNotNull(kwetterDTO.getUuid());
        assertEquals(kwetter.getUuid(), kwetterDTO.getUuid());
    }

    @Test
    void cloneAUserDTO() {
        User user = new User();

        UserDTO userDTO = new UserDTO(user);

        assertNotNull(userDTO);
        assertNotNull(userDTO.getUuid());
        assertEquals(user.getId(), userDTO.getUuid());
    }

    @Test
    void cloneACredentialsDTO() {
        Credentials credentials = new Credentials();
        credentials.setPassword("Test");

        CredentialsDTO credentialsDTO = new CredentialsDTO(credentials);

        assertNotNull(credentialsDTO);
        assertEquals(credentials.getPassword(), credentialsDTO.getPassword());
    }
}