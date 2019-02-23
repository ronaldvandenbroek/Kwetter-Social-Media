package nl.fontys.kwetter.mock;

import nl.fontys.kwetter.dao.UserDao;
import nl.fontys.kwetter.models.User;
import nl.fontys.kwetter.service.AdminService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(MockitoExtension.class)
class MockTest {
//
//    private AdminService adminService;
//
//    @Mock
//    UserDao userDao;
//
//    @BeforeEach
//    void setUp() {
//        adminService = new AdminService(userDao);
//        Mockito.lenient().when(userDao.getAllUsers()).thenReturn(new ArrayList<>());
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
//    @Test
//    void getAllUsers() {
//        assertNotNull(adminService.getAllUsers());
//    }
}