package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.Application;
import nl.fontys.kwetter.models.Credentials;
import nl.fontys.kwetter.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DatabaseUserUnitTests {

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void h2test() {
        User user = new User();
        user.setName("Test");

        new Credentials("1@test.nl", "test", user);

        User savedUser = userRepository.save(user);
        List<User> foundUser = userRepository.findByName(user.getName());

        assertNotNull(foundUser.get(0));
        assertEquals(savedUser.getName(), foundUser.get(0).getName());
    }
}
