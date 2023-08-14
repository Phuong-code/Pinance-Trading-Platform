package com.fdm.Pinance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fdm.Pinance.service.UserService;

import java.util.Collections;

import org.junit.jupiter.api.Test;

@WebMvcTest(LoginController.class)
class LoginControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"));
    }

    @Test
    void testLoginSuccess() throws Exception {
        String username = "testUser";
        String password = "testPassword";
        // Assuming isValidCredentials returns true for valid credentials
        when(userService.isValidCredentials(username, password)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/mainPage/" + username));
    }

    @Test
    void testLoginFailure() throws Exception {
        String username = "invalidUser";
        String password = "invalidPassword";
        // Assuming isValidCredentials returns false for invalid credentials
        when(userService.isValidCredentials(username, password)).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .param("username", username)
                .param("password", password))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("login"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("message"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Invalid username or password"));
    }
    
    @Test
    void testShowRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("registerUser"));
    }

}
