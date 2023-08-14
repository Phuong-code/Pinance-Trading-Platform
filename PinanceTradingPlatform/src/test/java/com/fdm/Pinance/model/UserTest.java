package com.fdm.Pinance.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.fdm.Pinance.service.AccountBalanceService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class UserTest {

//    @Autowired
//    private TestEntityManager entityManager;
	
	@Autowired
	private AccountBalanceService accountBalanceService;
    
    private User user;

    @BeforeEach
    public void setup() {
        user = new User("johnDoe", "password", "John", "Doe", "johndoe@example.com");
    }
    
    @Test
    public void testGetUsername() {
    	assertEquals("johnDoe", user.getUsername());
    }
    
    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }
    
    @Test
    public void testSetPassword() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }
    
    @Test
    public void testGetFirstName() {
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testSetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    public void testGetEmail() {
        assertEquals("johndoe@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("janesmith@example.com");
        assertEquals("janesmith@example.com", user.getEmail());
    }

    @Test
    public void testToString() {
        String expectedString = "User [username=johnDoe, password=password, firstName=John, lastName=Doe, email=johndoe@example.com]";
        assertEquals(expectedString, user.toString());
    }
}
