package com.fdm.Pinance.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fdm.Pinance.dal.UserRepository;
import com.fdm.Pinance.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByUsername_UserExists_ReturnsUser() {
        User user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
        when(userRepository.findById("johnDoe")).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername("johnDoe");

        assertNotNull(result);
        assertEquals("johnDoe", result.getUsername());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    public void testGetUserByUsername_UserDoesNotExist_ReturnsNull() {
        when(userRepository.findById("johnDoe")).thenReturn(Optional.empty());

        User result = userService.getUserByUsername("johnDoe");

        assertNull(result);
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("user1", "password", "User", "One", "userone@gmail.com");
        User user2 = new User("user2", "password", "User", "Two", "usertwo@gmail.com");
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testSaveUser() {
        User user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
        userService.saveUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
        userService.updateUser(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserByUsername() {
        User user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
        when(userRepository.findById("johnDoe")).thenReturn(Optional.of(user));

        userService.deleteUserByUsername("johnDoe");

        verify(userRepository, times(1)).deleteById("johnDoe");
    }
    
    @Test
    public void testDeleteAllUsers() {
        User user1 = new User("user1", "password", "User", "One", "userone@gmail.com");
        User user2 = new User("user2", "password", "User", "Two", "usertwo@gmail.com");
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        int count = userService.deleteAllUers();

        assertEquals(2, count);
        verify(userRepository, times(1)).delete(user1);
        verify(userRepository, times(1)).delete(user2);
    }
    
    @Test
    public void testIsValidCredentials_ValidCredentials_ReturnsTrue() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encodedPassword = bcrypt.encode("password");
        Optional<User> user = Optional.ofNullable(new User("johnDoe", encodedPassword, "John", "Doe", "johndoe@example.com"));
        when(userRepository.findById("johnDoe")).thenReturn(user);
        
        boolean result = userService.isValidCredentials("johnDoe", "password");
        
        assertTrue(result);
    }

    @Test
    public void testIsValidCredentials_InvalidUsername_ReturnsFalse() {
        when(userRepository.findByUsername("johnDoe")).thenReturn(null);

        boolean result = userService.isValidCredentials("johnDoe", "password");

        assertFalse(result);
    }

    @Test
    public void testIsValidCredentials_InvalidPassword_ReturnsFalse() {
        User user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
        when(userRepository.findByUsername("johnDoe")).thenReturn(user);

        boolean result = userService.isValidCredentials("johnDoe", "wrongPassword");

        assertFalse(result);
    }

}
