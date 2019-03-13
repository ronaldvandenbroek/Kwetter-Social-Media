package nl.fontys.kwetter.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.kwetter.controllers.KwetterController;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.implementation.KwetterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KwetterController.class)
public class KwetterControllerUnitTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private KwetterService kwetterService;

    @Test
    public void createKwetter() {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.setId(0L);
        user.setName("TestUser");

        Kwetter kwetter = new Kwetter("Test", user, Calendar.getInstance().getTime());
        try {
            when(kwetterService.createKwetter(0L, kwetter)).thenReturn(kwetter);

            mvc.perform(post("/kwetter/create/0")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(kwetter))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
