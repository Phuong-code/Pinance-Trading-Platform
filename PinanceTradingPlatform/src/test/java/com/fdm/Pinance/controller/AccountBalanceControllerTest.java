package com.fdm.Pinance.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.model.CryptoData;
import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.CryptoDataService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.junit.jupiter.MockitoExtension;


//@WebMvcTest(AccountBalanceController.class)
@ExtendWith(MockitoExtension.class)
class AccountBalanceControllerTest {

    @Mock
    private AccountBalanceService accountBalanceService;

    @Mock
    private CryptoDataService cryptoDataService;

    @InjectMocks
    private AccountBalanceController accountBalanceController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountBalanceController).build();
    }

    @Test
    void testShowAccountBalance() throws Exception {
        String username = "testUser";
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUsd(BigDecimal.valueOf(1000));
        accountBalance.setBtc(BigDecimal.valueOf(2));
        accountBalance.setEth(BigDecimal.valueOf(5));
        accountBalance.setDoge(BigDecimal.valueOf(1000));
        accountBalance.setXrp(BigDecimal.valueOf(300));
        accountBalance.setBnb(BigDecimal.valueOf(50));

        when(accountBalanceService.getAccountByUsername(username)).thenReturn(accountBalance);

        // Mocking crypto data map
        Map<String, BigDecimal> cryptoDataMap = new HashMap<>();
        cryptoDataMap.put("btc", BigDecimal.valueOf(40000));
        cryptoDataMap.put("eth", BigDecimal.valueOf(2500));
        cryptoDataMap.put("doge", BigDecimal.valueOf(0.3));
        cryptoDataMap.put("xrp", BigDecimal.valueOf(1.5));
        cryptoDataMap.put("bnb", BigDecimal.valueOf(350));

        when(cryptoDataService.getAllCryptoData()).thenReturn(Arrays.asList(
                new CryptoData("btc", BigDecimal.valueOf(40000)),
                new CryptoData("eth", BigDecimal.valueOf(2500)),
                new CryptoData("doge", BigDecimal.valueOf(0.3)),
                new CryptoData("xrp", BigDecimal.valueOf(1.5)),
                new CryptoData("bnb", BigDecimal.valueOf(350))
        ));

        mockMvc.perform(get("/accountBalance/{username}", username))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("accountBalance"))
                .andExpect(model().attributeExists("usdValue"))
                .andExpect(model().attributeExists("btcValue"))
                .andExpect(model().attributeExists("ethValue"))
                .andExpect(model().attributeExists("dogeValue"))
                .andExpect(model().attributeExists("xrpValue"))
                .andExpect(model().attributeExists("bnbValue"))
                .andExpect(model().attributeExists("totalValue"));
    }
    
//    @Test
//    void testGoBackToMainPage() throws Exception {
//        String username = "testUser";
//
//        mockMvc.perform(post("/goToMainPageFromAccountBalance"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/mainPage/" + username));
//    }

//    @Test
//    void testGoBackToMainPage() {
//        // Set the logginUsername
//        String username = "testUser";
//        accountBalanceController.setLogginUsername(username);
//
//        // Call the controller method
//        String viewName = accountBalanceController.goBackToMainPage();
//
//        // Verify the interaction and assertion
//        verify(accountBalanceService).getAccountByUsername(username);
//        // Verify that the view name returned is "redirect:/mainPage/{username}"
//        assertEquals("redirect:/mainPage/" + username, viewName);
//    }
    
    @Test
    void testGoBackToMainPage() {
        // Set the logginUsername
        String username = "testUser";
        accountBalanceController.setLogginUsername(username);

        // Call the controller method
        String viewName = accountBalanceController.goBackToMainPage();

        // Verify that the view name returned is "redirect:/mainPage/{username}"
        assertEquals("redirect:/mainPage/" + username, viewName);
    }
    
    @Test
    void testDepositUSD() {
        // Mock the request parameters
        BigDecimal amount = BigDecimal.valueOf(100);
        String username = "testUser";

        // Set the logginUsername
        accountBalanceController.setLogginUsername(username);

        // Call the controller method
        String viewName = accountBalanceController.depositUSD(amount);

        // Verify the interaction and assertion
        verify(accountBalanceService).depositUSD(username, amount);
        // Verify that the view name returned is "redirect:/accountBalance/{username}"
        assertEquals("redirect:/accountBalance/" + username, viewName);
    }

    @Test
    void testWithdrawUSD() {
        // Mock the request parameters
        BigDecimal amount = BigDecimal.valueOf(50);
        String username = "testUser";
        
        // Set the logginUsername
        accountBalanceController.setLogginUsername(username);

        // Call the controller method
        String viewName = accountBalanceController.withdrawUSD(amount);

        // Verify the interaction and assertion
        verify(accountBalanceService).withdrawUSD(username, amount);
        // Verify that the view name returned is "redirect:/accountBalance/{username}"
        assertEquals("redirect:/accountBalance/" + username, viewName);
    }
}
