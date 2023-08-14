package com.fdm.Pinance.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.UserService;
import com.fdm.Pinance.model.User;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AccountBalanceService accountBalanceService;

    @Test
    void testAddUserObject_UsernameAlreadyExists() throws Exception {
        // Given
        User existingUser = new User("existingUser", "password", "John", "Doe", "john.doe@example.com");
        when(userService.getUserByUsername(existingUser.getUsername())).thenReturn(existingUser);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/addUserObject")
                .param("username", existingUser.getUsername())
                .param("password", "password")
                .param("firstName", "John")
                .param("lastName", "Doe")
                .param("email", "john.doe@example.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("registerUser"))
                .andExpect(model().attributeExists("message"))
                .andExpect(model().attribute("message", "username already exists"));

        // Ensure the UserService method is called exactly once
        verify(userService, times(1)).getUserByUsername(existingUser.getUsername());
        verify(userService, times(0)).saveUser(any(User.class));
        verify(accountBalanceService, times(0)).createAccount(any(User.class));
    }

    @Test
    void testAddUserObject_NewUserRegisteredSuccessfully() throws Exception {
        // Given
        User newUser = new User("newUser", "password", "Jane", "Smith", "jane.smith@example.com");
        when(userService.getUserByUsername(newUser.getUsername())).thenReturn(null);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/addUserObject")
                .param("username", newUser.getUsername())
                .param("password", "password")
                .param("firstName", "Jane")
                .param("lastName", "Smith")
                .param("email", "jane.smith@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Ensure the UserService method is called exactly once
        verify(userService, times(1)).getUserByUsername(newUser.getUsername());
        verify(userService, times(1)).saveUser(any(User.class));
        verify(accountBalanceService, times(1)).createAccount(any(User.class));
    }

}
