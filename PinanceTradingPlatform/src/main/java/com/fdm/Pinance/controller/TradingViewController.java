package com.fdm.Pinance.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.CryptoDataService;

/**
 * Controller class that handles HTTP requests related to the trading view page.
 */
@Controller
public class TradingViewController {
	
    private final AccountBalanceService accountBalanceService;
    
    private final CryptoDataService cryptoDataService;
	
	private String logginUsername;
    
    private AccountBalance accountBalance;

	@Autowired
    public TradingViewController(AccountBalanceService accountBalanceService, CryptoDataService cryptoDataService) {
        this.accountBalanceService = accountBalanceService;
        this.cryptoDataService = cryptoDataService;
    }

	/**
	 * Handler method to display the trading view page.
	 * 
	 * @param username The username of the logged-in user.
	 * @param model The Model object to pass data to the view.
	 * @return The name of the HTML template used to render the trading view page.
	 */
	@GetMapping("market/{username}")
	public String showMarket(@PathVariable String username, Model model) {
		logginUsername = username;
		accountBalance = accountBalanceService.getAccountByUsername(logginUsername);
		return "tradingView";
	}
	
	/**
	 * Handler method to navigate back to the main page.
	 * 
	 * @return A redirect to the main page for the logged-in user.
	 */
	@PostMapping("/goToMainPageFromTradingView")
	public String goBackToMainPage() {
		return "redirect:/mainPage/"+logginUsername;
	}
	
	/**
	 * Handler method to execute a cryptocurrency purchase.
	 * 
	 * @param buyAmount The amount of cryptocurrency to buy.
	 * @param cryptoSymbol The symbol of the cryptocurrency to buy.
	 */
    @PostMapping("/buyCrypto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void buyCrypto(@RequestParam BigDecimal buyAmount, @RequestParam String cryptoSymbol) {
    	String cryptoSymbolTrimmed = cryptoSymbol.replace("USDT", "").toLowerCase();
    	accountBalanceService.buyCrypto(logginUsername, buyAmount, cryptoSymbolTrimmed);
    }
    
	/**
	 * Handler method to execute a cryptocurrency sale.
	 * 
	 * @param sellAmount The amount of cryptocurrency to sell.
	 * @param cryptoSymbol The symbol of the cryptocurrency to sell.
	 */
    @PostMapping("/sellCrypto")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sellCrypto(@RequestParam BigDecimal sellAmount, @RequestParam String cryptoSymbol) {
    	String cryptoSymbolTrimmed = cryptoSymbol.replace("USDT", "").toLowerCase();
    	accountBalanceService.sellCrypto(logginUsername, sellAmount, cryptoSymbolTrimmed);
    }

}
