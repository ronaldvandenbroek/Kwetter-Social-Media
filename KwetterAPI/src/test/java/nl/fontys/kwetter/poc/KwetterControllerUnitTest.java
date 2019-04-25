package nl.fontys.kwetter.poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.fontys.kwetter.models.Kwetter;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.IKwetterService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(secure = false)
class KwetterControllerUnitTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private IKwetterService kwetterService;
//
//    @Test
//    void createKwetter() {
//        ObjectMapper mapper = new ObjectMapper();
//
//        User user = new User();
//        user.setName("TestUser");
//
//        Kwetter kwetter = new Kwetter("Test", user, Calendar.getInstance().getTime());
//        try {
//            Mockito.when(kwetterService.createKwetter(user.getId(), kwetter)).thenReturn(kwetter);
//
//            mvc.perform(post("/kwetter/create/" + user.getId())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(mapper.writeValueAsString(kwetter))
//                    .accept(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk());
//        } catch (Exception e) {
//            e.printStackTrace();
//            fail("");
//        }
//    }
}
