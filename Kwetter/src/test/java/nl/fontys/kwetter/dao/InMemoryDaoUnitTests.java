package nl.fontys.kwetter.dao;

import nl.fontys.kwetter.dao.memory.InMemoryCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testing the In Memory DAO")
public class InMemoryDaoUnitTests {

    @AfterEach
    void tearDown() {
        InMemoryCollection.resetMemory();
    }

    @Test
    @DisplayName("A created kwetter should be added to the user")
    void createAKwetter() {
        assertEquals(1, user.getCreatedKwetters().size());
        assertTrue(user.getCreatedKwetters().contains(kwetter));
    }
}
