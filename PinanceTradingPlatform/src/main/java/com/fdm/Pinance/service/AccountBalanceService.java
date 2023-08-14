package com.fdm.Pinance.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdm.Pinance.dal.AccountBalanceRepository;
import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.model.CryptoData;
import com.fdm.Pinance.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service class that provides business logic and operations for managing AccountBalance entities.
 */
@Service
public class AccountBalanceService {

    private final AccountBalanceRepository accountBalanceRepository;
    
    private final CryptoDataService cryptoDataService;

    private final Logger logger = LoggerFactory.getLogger(AccountBalanceService.class);

    
    @Autowired
    public AccountBalanceService(AccountBalanceRepository accountBalanceRepository, CryptoDataService cryptoDataService) {
        this.accountBalanceRepository = accountBalanceRepository;
        this.cryptoDataService = cryptoDataService;
    }

    /**
     * Creates a new AccountBalance entity and associates it with the provided User.
     *
     * @param user The User entity to associate with the new AccountBalance.
     * @return The newly created AccountBalance entity.
     */
    public AccountBalance createAccount(User user) {
        AccountBalance account = new AccountBalance();
        account.setUser(user);
        account.setBnb(BigDecimal.valueOf(0));
        account.setEth(BigDecimal.valueOf(0));
        account.setDoge(BigDecimal.valueOf(0));
        account.setXrp(BigDecimal.valueOf(0));
        account.setBtc(BigDecimal.valueOf(0));
        account.setUsd(BigDecimal.valueOf(0));

        return accountBalanceRepository.save(account);
    }
    
    /**
     * Retrieves an AccountBalance entity by its ID.
     *
     * @param id The ID of the AccountBalance to retrieve.
     * @return The AccountBalance entity with the specified ID, or null if not found.
     */
    public AccountBalance getAccountById(Long id) {
    	Optional<AccountBalance> accountBalance = accountBalanceRepository.findById(id);
    	if(accountBalance.isPresent())			
			return accountBalance.get();
		else 
			return null;
    }
    
    /**
     * Retrieves an AccountBalance entity by the username of its associated User.
     *
     * @param username The username of the User associated with the AccountBalance.
     * @return The AccountBalance entity associated with the specified username, or null if not found.
     */
    public AccountBalance getAccountByUsername(String username) {
        return accountBalanceRepository.findByUsername(username);
    }

    /**
     * Retrieves a list of all AccountBalance entities.
     *
     * @return A list of all AccountBalance entities in the database.
     */
    public List<AccountBalance> getAllAccounts() {
        return accountBalanceRepository.findAll();
    }

    /**
     * Updates an existing AccountBalance entity.
     *
     * @param account The AccountBalance entity to update.
     * @return The updated AccountBalance entity.
     */
    public AccountBalance updateAccount(AccountBalance account) {
        return accountBalanceRepository.save(account);
    }

    /**
     * Deletes an AccountBalance entity by its ID.
     *
     * @param accountId The ID of the AccountBalance to delete.
     */
    public void deleteAccount(Long accountId) {
    	accountBalanceRepository.deleteById(accountId);
    }
    
    /**
     * Deposits the specified amount of USD into the account associated with the given username.
     *
     * @param username The username of the account to deposit USD into.
     * @param amount   The amount of USD to deposit.
     */
    public void depositUSD(String username, BigDecimal amount) {
    	AccountBalance accountBalance = getAccountByUsername(username);
        BigDecimal currentBalance = accountBalance.getUsd();
        BigDecimal newBalance = currentBalance.add(amount);
        accountBalance.setUsd(newBalance);
        accountBalanceRepository.save(accountBalance);
        logger.info("User '{}' deposited ${}.", username, amount);
    }
    
    /**
     * Withdraws the specified amount of USD from the account associated with the given username.
     *
     * @param username The username of the account to withdraw USD from.
     * @param amount   The amount of USD to withdraw.
     */
    public void withdrawUSD(String username, BigDecimal amount) {
    	AccountBalance accountBalance = getAccountByUsername(username);
        BigDecimal currentBalance = accountBalance.getUsd();
        // Check if the withdrawal amount does not exceed the current USD balance
        if (amount.compareTo(currentBalance) <= 0) {
            BigDecimal newBalance = currentBalance.subtract(amount);
            accountBalance.setUsd(newBalance);
            accountBalanceRepository.save(accountBalance);
            logger.info("User '{}' withdrew ${}.", username, amount);        } 
    }
    
    /**
     * Buys the specified amount of a cryptocurrency with USD in the account associated with the given username.
     *
     * @param username     The username of the account to make the purchase from.
     * @param buyAmount    The amount of the cryptocurrency to buy.
     * @param cryptoSymbol The symbol of the cryptocurrency to buy (e.g., "BTC").
     */
    public void buyCrypto(String username, BigDecimal buyAmount, String cryptoSymbol) {
        // Fetch all the required CryptoData objects in one call
        Map<String, BigDecimal> cryptoDataMap = new HashMap<>();
        List<CryptoData> cryptoDataList = cryptoDataService.getAllCryptoData();
        for (CryptoData cryptoData : cryptoDataList) {
            cryptoDataMap.put(cryptoData.getSymbol().toLowerCase(), cryptoData.getPrice());
        }
    	
    	AccountBalance accountBalance = getAccountByUsername(username);
    	BigDecimal currentUSDBalance = accountBalance.getUsd();
    	BigDecimal currentCryptoAmount = accountBalanceRepository.getCryptoAmount(username, cryptoSymbol);
    	BigDecimal buyCryptoPrice = buyAmount.multiply(cryptoDataMap.get(cryptoSymbol));
    	// Check if the buy value does not exceed the current USD balance
    	if (buyCryptoPrice.compareTo(currentUSDBalance) <= 0) {
    		BigDecimal newCryptoAmount = currentCryptoAmount.add(buyAmount);
    		BigDecimal newCryptoBalance = buyAmount.multiply(cryptoDataMap.get(cryptoSymbol));
    		BigDecimal newUSDBalance = currentUSDBalance.subtract(newCryptoBalance);
    		accountBalanceRepository.updateCryptoAmount(username, cryptoSymbol, newCryptoAmount);
    		accountBalanceRepository.updateCryptoAmount(username, "usd", newUSDBalance);	
            logger.info("User '{}' bought {} {} with ${}.", username, buyAmount, cryptoSymbol.toUpperCase(), buyCryptoPrice);
    	} 
    }
    
    /**
     * Sells the specified amount of a cryptocurrency for USD in the account associated with the given username.
     *
     * @param username     The username of the account to make the sale from.
     * @param sellAmount   The amount of the cryptocurrency to sell.
     * @param cryptoSymbol The symbol of the cryptocurrency to sell (e.g., "BTC").
     */
    public void sellCrypto(String username, BigDecimal sellAmount, String cryptoSymbol) {
        // Fetch all the required CryptoData objects in one call
        Map<String, BigDecimal> cryptoDataMap = new HashMap<>();
        List<CryptoData> cryptoDataList = cryptoDataService.getAllCryptoData();
        for (CryptoData cryptoData : cryptoDataList) {
            cryptoDataMap.put(cryptoData.getSymbol().toLowerCase(), cryptoData.getPrice());
        }
    	AccountBalance accountBalance = getAccountByUsername(username);
    	BigDecimal currentUSDBalance = accountBalance.getUsd();
    	BigDecimal currentCryptoAmount = accountBalanceRepository.getCryptoAmount(username, cryptoSymbol);
    	BigDecimal sellCryptoPrice = sellAmount.multiply(cryptoDataMap.get(cryptoSymbol));
    	// Check if the sell amount does not exceed the current Crypto Amount in account balance
    	if (currentCryptoAmount.compareTo(sellAmount) >= 0) {
    		BigDecimal newCryptoAmount = currentCryptoAmount.subtract(sellAmount);
    		BigDecimal newUSDBalance = currentUSDBalance.add(sellCryptoPrice);
    		accountBalanceRepository.updateCryptoAmount(username, cryptoSymbol, newCryptoAmount);
    		accountBalanceRepository.updateCryptoAmount(username, "usd", newUSDBalance);	
            logger.info("User '{}' sold {} {} with ${}.", username, sellAmount, cryptoSymbol.toUpperCase(), sellCryptoPrice);
    	} 
    }
}

