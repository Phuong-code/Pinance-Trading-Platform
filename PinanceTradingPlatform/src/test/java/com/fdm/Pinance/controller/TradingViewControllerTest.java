package com.fdm.Pinance.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.CryptoDataService;

import static org.mockito.Mockito.*;

@WebMvcTest(TradingViewController.class)
class TradingViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountBalanceService accountBalanceService;

    @MockBean
    private CryptoDataService cryptoDataService;
    
    @Test
    void testShowMarket() throws Exception {
        // Given
        String username = "testUser";
        AccountBalance accountBalance = new AccountBalance();
        when(accountBalanceService.getAccountByUsername(username)).thenReturn(accountBalance);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/market/" + username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("tradingView"));
    }

    @Test
    void testGoBackToMainPage() throws Exception {
        // Given
        String username = "testUser";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/goToMainPageFromTradingView"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/mainPage/" + username));
    }
    
    @Test
    void testBuyCrypto() throws Exception {
        // Given
        String username = "testUser";
        BigDecimal buyAmount = new BigDecimal("100.0");
        String cryptoSymbol = "BTCUSDT";

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/buyCrypto")
                .param("buyAmount", buyAmount.toString())
                .param("cryptoSymbol", cryptoSymbol)
                .param("username", username))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Ensure the AccountBalanceService method is called with the correct arguments
        String cryptoSymbolTrimmed = cryptoSymbol.replace("USDT", "").toLowerCase();
        verify(accountBalanceService, times(1)).buyCrypto(username, buyAmount, cryptoSymbolTrimmed);
    }
    
    @Test
    void testSellCrypto() throws Exception {
        // Given
        String username = "testUser";
        BigDecimal sellAmount = new BigDecimal("50.0");
        String cryptoSymbol = "ETHUSDT";
        
        // Set the logginUsername before calling the method
        when(accountBalanceService.getAccountByUsername(username)).thenReturn(new AccountBalance());
        mockMvc.perform(MockMvcRequestBuilders.get("/market/" + username));

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/sellCrypto")
                .param("sellAmount", sellAmount.toString())
                .param("cryptoSymbol", cryptoSymbol)
                .param("username", username))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        // Ensure the AccountBalanceService method is called with the correct arguments
        String cryptoSymbolTrimmed = cryptoSymbol.replace("USDT", "").toLowerCase();
        verify(accountBalanceService, times(1)).sellCrypto(username, sellAmount, cryptoSymbolTrimmed);
    }

}
