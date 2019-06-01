package nl.fontys.kwetter.service;

import nl.fontys.kwetter.configuration.InMemoryTestConfiguration;
import nl.fontys.kwetter.exceptions.FailedToAddLinksException;
import nl.fontys.kwetter.models.dto.KwetterDTO;
import nl.fontys.kwetter.models.dto.UserDTO;
import nl.fontys.kwetter.models.entity.Kwetter;
import nl.fontys.kwetter.models.entity.User;
import nl.fontys.kwetter.repository.memory.implementation.data.manager.IInMemoryDatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("Testing the Hateoas Service")
@DataJpaTest
@Import(InMemoryTestConfiguration.class)
@Transactional
class HateoasServiceIntegrationTest {

    private List<Kwetter> testKwetters;
    private List<User> testUsers;
    private Kwetter kwetter;
    private User user;

    @Autowired
    private IInMemoryDatabaseManager inMemoryDatabaseManager;

    @Autowired
    private IHateoasService hateoasService;

    @BeforeEach
    void setUp() {
        inMemoryDatabaseManager.reset();

        kwetter = new Kwetter();
        kwetter.setText("test1");
        testKwetters = new ArrayList<>();
        testKwetters.add(new Kwetter());
        testKwetters.add(new Kwetter());

        user = new User();
        user.setName("test1");
        testUsers = new ArrayList<>();
        testUsers.add(user);
        testUsers.add(new User());
    }

    @Test
    void getUserDTOWithLinksSingle() {
        try {
            UserDTO userDTO = hateoasService.getUserDTOWithLinks(user);
            assertEquals(user.getName(), userDTO.getName());
            assertEquals(13, userDTO.getLinks().size());
        } catch (FailedToAddLinksException e) {
            fail("This exception should not have been thrown.");
        }
    }

    @Test
    void getUserDTOWithLinksMultiple() {
        try {
            List<UserDTO> userDTOs = hateoasService.getUserDTOWithLinks(testUsers);
            assertEquals(testUsers.size(), userDTOs.size());
        } catch (FailedToAddLinksException e) {
            fail("This exception should not have been thrown.");
        }
    }

    @Test
    void getKwetterDTOWithLinksSingle() {
        try {
            KwetterDTO kwetterDTO = hateoasService.getKwetterDTOWithLinks(kwetter);
            assertEquals(kwetter.getText(), kwetterDTO.getText());
            assertEquals(1, kwetterDTO.getLinks().size());
        } catch (FailedToAddLinksException e) {
            fail("This exception should not have been thrown.");
        }
    }

    @Test
    void getKwetterWithLinksMultiple() {
        try {
            List<KwetterDTO> kwetterDTOs = hateoasService.getKwetterDTOWithLinks(testKwetters);
            assertEquals(testKwetters.size(), kwetterDTOs.size());
        } catch (FailedToAddLinksException e) {
            fail("This exception should not have been thrown.");
        }
    }
}