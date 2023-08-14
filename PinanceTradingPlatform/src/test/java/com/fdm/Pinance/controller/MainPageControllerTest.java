package com.fdm.Pinance.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fdm.Pinance.model.User;
import com.fdm.Pinance.service.UserService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



import org.junit.jupiter.api.Test;

@WebMvcTest(MainPageController.class)
class MainPageControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
	
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    void testShowMainPage() throws Exception {
        String username = "testUser";
        when(userService.getUserByUsername(username)).thenReturn(new User(username, "password", "user1", "Test", "usertest@gmail.com"));

        mockMvc.perform(get("/mainPage/" + username))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPage"));
    }

    @Test
    void testOpenMarket() throws Exception {
        String username = "testUser";

        mockMvc.perform(post("/openMarket"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/market/" + username));
    }

    @Test
    void testExit() throws Exception {
        mockMvc.perform(post("/exit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    
    @Test
    void testOpenAccountBalance() throws Exception {
        String username = "testUser";
        when(userService.getUserByUsername(username)).thenReturn(new User(username, "password", "user1", "Test", "usertest@gmail.com"));

        mockMvc.perform(get("/mainPage/" + username))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPage"));

        mockMvc.perform(post("/openAccountBalance").sessionAttr("logginUsername", username))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accountBalance/" + username));
    }
}
