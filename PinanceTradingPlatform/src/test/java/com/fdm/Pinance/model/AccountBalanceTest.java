package com.fdm.Pinance.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.fdm.Pinance.dal.AccountBalanceRepository;

@SpringBootTest
class AccountBalanceTest {

    // Mock the User class
    @MockBean
    private User user;

    @Autowired
    private AccountBalanceRepository accountBalanceRepository;

    // Create an instance of AccountBalance for testing
    private AccountBalance accountBalance;

    @BeforeEach
    public void setUp() {
        accountBalance = new AccountBalance(user,
                new BigDecimal("100.0000"), // Sample initial values
                new BigDecimal("50.0000"),
                new BigDecimal("200.0000"),
                new BigDecimal("300.0000"),
                new BigDecimal("400.0000"),
                new BigDecimal("1000.0000"));
    }
    
    @Test
    public void testGetId() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        // Set a value for the ID using reflection or any other appropriate method
        Long expectedId = 1L;
        setIdUsingReflection(accountBalance, expectedId);

        // Call the method to be tested
        Long actualId = accountBalance.getId();

        // Verify that the ID returned by the method matches the expected value
        assertEquals(expectedId, actualId);
    }

    // Helper method to set the ID using reflection (you can adjust this based on your implementation)
    private void setIdUsingReflection(AccountBalance accountBalance, Long id) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        java.lang.reflect.Field idField = AccountBalance.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(accountBalance, id);
    }

    @Test
    public void testGetters() {
        // Verify that getters return the same values set in the constructor
        assertEquals(user, accountBalance.getUser());
        assertEquals(new BigDecimal("100.0000"), accountBalance.getBnb());
        assertEquals(new BigDecimal("50.0000"), accountBalance.getBtc());
        assertEquals(new BigDecimal("200.0000"), accountBalance.getDoge());
        assertEquals(new BigDecimal("300.0000"), accountBalance.getEth());
        assertEquals(new BigDecimal("400.0000"), accountBalance.getXrp());
        assertEquals(new BigDecimal("1000.0000"), accountBalance.getUsd());
    }
    
    @Test
    public void testSetUser() {
        // Create a mock User object
        User mockUser = mock(User.class);

        // Set up the mock to return a specific username when calling getUsername()
        String username = "testUser";
        when(mockUser.getUsername()).thenReturn(username);

        // Call the method to be tested
        accountBalance.setUser(mockUser);

        // Verify that the User object was correctly set
        assertEquals(mockUser, accountBalance.getUser());

        // Verify that the username returned by the mock matches the one in the AccountBalance
        assertEquals(username, accountBalance.getUser().getUsername());
    }
    
    @Test
    public void testCryptoSetters() {
        // Test the setters
        BigDecimal newBnb = new BigDecimal("200.0000");
        accountBalance.setBnb(newBnb);
        assertEquals(newBnb, accountBalance.getBnb());
        
        BigDecimal newBtc = new BigDecimal("300.0000");
        accountBalance.setBtc(newBtc);
        assertEquals(newBtc, accountBalance.getBtc());
        
        BigDecimal newDoge = new BigDecimal("400.0000");
        accountBalance.setDoge(newDoge);
        assertEquals(newDoge, accountBalance.getDoge());
        
        BigDecimal newEth = new BigDecimal("500.0000");
        accountBalance.setEth(newEth);
        assertEquals(newEth, accountBalance.getEth());
        
        BigDecimal newXrp = new BigDecimal("600.0000");
        accountBalance.setXrp(newXrp);
        assertEquals(newXrp, accountBalance.getXrp());

        BigDecimal newUsd = new BigDecimal("700.0000");
        accountBalance.setUsd(newUsd);
        assertEquals(newUsd, accountBalance.getUsd());
    }

    @Test
    public void testToString() {
        // Verify that the toString method returns a meaningful string representation
        String expected = "Accounts [id=null, user=" + user + ", bnb=100.0000, btc=50.0000, doge=200.0000, eth=300.0000, xrp=400.0000, usd=1000.0000]";
        assertEquals(expected, accountBalance.toString());
    }

}
