package com.fdm.Pinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fdm.Pinance.dal.UserRepository;
import com.fdm.Pinance.model.AccountBalance;
import com.fdm.Pinance.model.CryptoData;
import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.CryptoDataService;
import com.fdm.Pinance.service.UserService;
import com.fdm.Pinance.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class that handles HTTP requests related to account balance functionality.
 */
@Controller
public class AccountBalanceController {

    private final AccountBalanceService accountBalanceService;
    
    private final CryptoDataService cryptoDataService;
	
    private String logginUsername;
    
    private AccountBalance accountBalance;

	@Autowired
    public AccountBalanceController(AccountBalanceService accountService, CryptoDataService cryptoDataService) {
        this.accountBalanceService = accountService;
        this.cryptoDataService = cryptoDataService;
    }

    /**
     * Handler method to display the account balance page for a specific user.
     * 
     * @param username The username of the logged-in user.
     * @param model    The model object to pass data to the view.
     * @return The name of the HTML template used to render the account balance page.
     */
    @GetMapping("/accountBalance/{username}")
    public String showAccountBalance(@PathVariable String username, Model model) {
        // Retrieve the account balance data from the database
    	logginUsername = username;
        accountBalance = accountBalanceService.getAccountByUsername(logginUsername);

        // Pass the account balance data to the HTML template
        model.addAttribute("accountBalance", accountBalance);
        
        // Fetch all the required CryptoData objects in one call
        Map<String, BigDecimal> cryptoDataMap = new HashMap<>();
        List<CryptoData> cryptoDataList = cryptoDataService.getAllCryptoData();
        for (CryptoData cryptoData : cryptoDataList) {
            cryptoDataMap.put(cryptoData.getSymbol().toLowerCase(), cryptoData.getPrice());
        }

        // Calculate the values and add them to the model
        BigDecimal usdValue = accountBalance.getUsd().setScale(4, RoundingMode.HALF_UP);
        BigDecimal btcValue = accountBalance.getBtc().multiply(cryptoDataMap.get("btc")).setScale(4, RoundingMode.HALF_UP);
        BigDecimal ethValue = accountBalance.getEth().multiply(cryptoDataMap.get("eth")).setScale(4, RoundingMode.HALF_UP);
        BigDecimal dogeValue = accountBalance.getDoge().multiply(cryptoDataMap.get("doge")).setScale(4, RoundingMode.HALF_UP);
        BigDecimal xrpValue = accountBalance.getXrp().multiply(cryptoDataMap.get("xrp")).setScale(4, RoundingMode.HALF_UP);
        BigDecimal bnbValue = accountBalance.getBnb().multiply(cryptoDataMap.get("bnb")).setScale(4, RoundingMode.HALF_UP);

        model.addAttribute("usdValue", usdValue);
        model.addAttribute("btcValue", btcValue);
        model.addAttribute("ethValue", ethValue);
        model.addAttribute("dogeValue", dogeValue);
        model.addAttribute("xrpValue", xrpValue);
        model.addAttribute("bnbValue", bnbValue);
        
        // Calculate the total value
        BigDecimal totalValue = usdValue.add(btcValue).add(ethValue).add(dogeValue).add(xrpValue).add(bnbValue);
        model.addAttribute("totalValue", totalValue);

        return "accountBalance";
    }
    
    /**
     * Handler method to navigate back to the main page from the account balance page.
     * 
     * @return A redirection to the main page for the logged-in user.
     */
	@PostMapping("/goToMainPageFromAccountBalance")
	public String goBackToMainPage() {
		return "redirect:/mainPage/"+logginUsername;
	}
	
    /**
     * Handler method to deposit USD into the user's account.
     * 
     * @param amount The amount of USD to be deposited.
     * @return A redirection to the account balance page for the logged-in user.
     */
    @PostMapping("/depositUSD")
    public String depositUSD(@RequestParam BigDecimal amount) {
    	accountBalanceService.depositUSD(logginUsername, amount);
        return "redirect:/accountBalance/" + logginUsername;
    }
    
    /**
     * Handler method to withdraw USD from the user's account.
     * 
     * @param amount The amount of USD to be withdrawn.
     * @return A redirection to the account balance page for the logged-in user.
     */
    @PostMapping("/withdrawUSD")
    public String withdrawUSD(@RequestParam BigDecimal amount) {
    	accountBalanceService.withdrawUSD(logginUsername, amount);
        return "redirect:/accountBalance/" + logginUsername;
    }
    
    /**
     * Setter method to set the logged-in username.
     * 
     * @param logginUsername The username of the logged-in user.
     */
    public void setLogginUsername(String logginUsername) {
    	this.logginUsername = logginUsername;
    }
    
}
