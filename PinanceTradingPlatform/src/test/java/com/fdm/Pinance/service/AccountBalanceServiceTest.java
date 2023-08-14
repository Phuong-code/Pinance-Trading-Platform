package com.fdm.Pinance.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.fdm.Pinance.dal.AccountBalanceRepository;
import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.model.User;
import com.fdm.Pinance.model.CryptoData;
import com.fdm.Pinance.service.CryptoDataService;

//@SpringBootTest
class AccountBalanceServiceTest {
	
    @Mock
    private AccountBalanceRepository accountBalanceRepository;

    @Mock
    private CryptoDataService cryptoDataService;

    @InjectMocks
    private AccountBalanceService accountBalanceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAccount() {
        User user = new User();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(user);
        accountBalance.setUsd(BigDecimal.valueOf(0));

        when(accountBalanceRepository.save(any(AccountBalance.class))).thenReturn(accountBalance);

        AccountBalance createdAccount = accountBalanceService.createAccount(user);

        assertEquals(user, createdAccount.getUser());
        assertEquals(BigDecimal.valueOf(0), createdAccount.getUsd());
    }

    @Test
    public void testGetAccountById() {
        Long accountId = 1L;
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(accountId);

        when(accountBalanceRepository.findById(accountId)).thenReturn(Optional.of(accountBalance));

        AccountBalance retrievedAccount = accountBalanceService.getAccountById(accountId);

        assertEquals(accountId, retrievedAccount.getId());
    }
    
    @Test
    public void testGetAccountById_returnNull() {
        Long accountId = 1L;
        when(accountBalanceRepository.findById(accountId)).thenReturn(Optional.empty());

        AccountBalance retrievedAccount = accountBalanceService.getAccountById(accountId);

        assertNull(retrievedAccount);
    }

    @Test
    public void testGetAccountByUsername() {
        String username = "testUser";
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(new User(username, "password", "user1", "Test", "usertest@gmail.com"));

        when(accountBalanceRepository.findByUsername(username)).thenReturn(accountBalance);

        AccountBalance retrievedAccount = accountBalanceService.getAccountByUsername(username);

        assertEquals(username, retrievedAccount.getUser().getUsername());
    }

    @Test
    public void testGetAllAccounts() {
        List<AccountBalance> accountList = new ArrayList<>();
        accountList.add(new AccountBalance());
        accountList.add(new AccountBalance());

        when(accountBalanceRepository.findAll()).thenReturn(accountList);

        List<AccountBalance> retrievedAccounts = accountBalanceService.getAllAccounts();

        assertEquals(2, retrievedAccounts.size());
    }
    
    @Test
    void testUpdateAccount() {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setId(1L);
        accountBalance.setUsd(BigDecimal.valueOf(100));

        when(accountBalanceRepository.save(any(AccountBalance.class))).thenReturn(accountBalance);

        AccountBalance updatedAccount = accountBalanceService.updateAccount(accountBalance);

        assertEquals(accountBalance.getId(), updatedAccount.getId());
        assertEquals(accountBalance.getUsd(), updatedAccount.getUsd());
    }
    
    @Test
    void testDeleteAccount() {
        Long accountId = 1L;

        accountBalanceService.deleteAccount(accountId);

        verify(accountBalanceRepository, times(1)).deleteById(accountId);
    }
    
    @Test
    void testDepositUSD() {
        String username = "testUser";
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        BigDecimal depositAmount = BigDecimal.valueOf(50);

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(new User(username, "password", "user1", "Test", "usertest@gmail.com"));
        accountBalance.setUsd(initialBalance);

        when(accountBalanceRepository.findByUsername(username)).thenReturn(accountBalance);
        when(accountBalanceRepository.save(any(AccountBalance.class))).thenReturn(accountBalance);

        accountBalanceService.depositUSD(username, depositAmount);

        assertEquals(initialBalance.add(depositAmount), accountBalance.getUsd());
        verify(accountBalanceRepository, times(1)).save(accountBalance);
    }
    
    @Test
    void testWithdrawUSD() {
        String username = "testUser";
        BigDecimal initialBalance = BigDecimal.valueOf(100);
        BigDecimal withdrawalAmount = BigDecimal.valueOf(50);

        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(new User(username, "password", "user1", "Test", "usertest@gmail.com"));
        accountBalance.setUsd(initialBalance);

        when(accountBalanceRepository.findByUsername(username)).thenReturn(accountBalance);
        when(accountBalanceRepository.save(any(AccountBalance.class))).thenReturn(accountBalance);

        accountBalanceService.withdrawUSD(username, withdrawalAmount);

        assertEquals(initialBalance.subtract(withdrawalAmount), accountBalance.getUsd());
        verify(accountBalanceRepository, times(1)).save(accountBalance);
    }
    
    @Test
    public void testBuyCrypto_withSufficientBalance() {
        // Set up test data
        String username = "testUser";
        BigDecimal buyAmount = BigDecimal.ONE;
        String cryptoSymbol = "btc";
        BigDecimal cryptoPrice = BigDecimal.valueOf(30000);

        // Mock the behavior of cryptoDataService
        List<CryptoData> cryptoDataList = new ArrayList<>();
        CryptoData cryptoData = new CryptoData();
        cryptoData.setSymbol(cryptoSymbol); // Ensure cryptoSymbol matches with the input format
        cryptoData.setPrice(cryptoPrice);
        cryptoDataList.add(cryptoData);
        when(cryptoDataService.getAllCryptoData()).thenReturn(cryptoDataList);

        // Set up the account balance
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(new User(username, "password", "user1", "Test", "usertest@gmail.com")); // Assuming the constructor expects a User object
        accountBalance.setUsd(BigDecimal.valueOf(50000));
        accountBalance.setBtc(BigDecimal.ZERO); // Assuming there's no existing crypto amount

        // Mock the behavior of getAccountByUsername in the service to return the account balance
        when(accountBalanceService.getAccountByUsername(username)).thenReturn(accountBalance);

        // Mock the behavior of getCryptoAmount to return the current crypto amount
        when(accountBalanceRepository.getCryptoAmount(username, cryptoSymbol)).thenReturn(BigDecimal.ZERO);

        // Perform the method under test
        accountBalanceService.buyCrypto(username, buyAmount, cryptoSymbol);

        // Calculate expected results
//        BigDecimal expectedNewCryptoAmount = buyAmount;
//        BigDecimal expectedNewUSDBalance = BigDecimal.valueOf(50000).subtract(buyAmount.multiply(cryptoPrice));

        // Verify the changes
        verify(accountBalanceRepository, times(1)).updateCryptoAmount(eq(username), eq(cryptoSymbol), any(BigDecimal.class));
        verify(accountBalanceRepository, times(1)).updateCryptoAmount(eq(username), eq("usd"), any(BigDecimal.class));
        
//        assertEquals(expectedNewCryptoAmount, accountBalance.getBtc());
//        assertEquals(expectedNewUSDBalance, accountBalance.getUsd());
    }
    
    @Test
    public void testSellCrypto() {
        // Set up test data
        String username = "testUser";
        BigDecimal sellAmount = BigDecimal.ONE;
        String cryptoSymbol = "btc";
        BigDecimal cryptoPrice = BigDecimal.valueOf(30000);

        // Mock the behavior of cryptoDataService
        List<CryptoData> cryptoDataList = new ArrayList<>();
        CryptoData cryptoData = new CryptoData();
        cryptoData.setSymbol(cryptoSymbol); // Ensure cryptoSymbol matches with the input format
        cryptoData.setPrice(cryptoPrice);
        cryptoDataList.add(cryptoData);
        when(cryptoDataService.getAllCryptoData()).thenReturn(cryptoDataList);

        // Set up the account balance
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setUser(new User(username, "password", "user1", "Test", "usertest@gmail.com")); // Assuming the constructor expects a User object
        accountBalance.setUsd(BigDecimal.valueOf(50000));
        accountBalance.setBtc(BigDecimal.ONE); // Assuming there's one BTC as the existing crypto amount

        // Mock the behavior of getAccountByUsername in the service to return the account balance
        when(accountBalanceService.getAccountByUsername(username)).thenReturn(accountBalance);

        // Mock the behavior of getCryptoAmount to return the current crypto amount
        when(accountBalanceRepository.getCryptoAmount(username, cryptoSymbol)).thenReturn(BigDecimal.ONE);

        // Perform the method under test
        accountBalanceService.sellCrypto(username, sellAmount, cryptoSymbol);

        // Calculate expected results
//        BigDecimal expectedNewCryptoAmount = BigDecimal.ZERO;
//        BigDecimal expectedNewUSDBalance = BigDecimal.valueOf(50000).add(sellAmount.multiply(cryptoPrice));

        // Verify the changes
        verify(accountBalanceRepository, times(1)).updateCryptoAmount(eq(username), eq(cryptoSymbol), any(BigDecimal.class));
        verify(accountBalanceRepository, times(1)).updateCryptoAmount(eq(username), eq("usd"), any(BigDecimal.class));
        
//        assertEquals(expectedNewCryptoAmount, accountBalance.getBtc());
//        assertEquals(expectedNewUSDBalance, accountBalance.getUsd());
    }


}
