package com.fdm.Pinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.Pinance.service.UserService;

/**
 * Controller class that handles HTTP requests related to the main page.
 */
@Controller
public class MainPageController {
	
	@Autowired
	private UserService userService;
	
	private String logginUsername;

	/**
	 * Handler method to display the main page for a specific user.
	 * 
	 * @param username The username of the logged-in user.
	 * @return The name of the HTML template used to render the main page.
	 */
	@GetMapping("/mainPage/{username}")
	public String showMainPage(@PathVariable String username) {
    	logginUsername = username;
	    return "mainPage";
	}
	
	/**
	 * Handler method to open the account balance page for the logged-in user.
	 * 
	 * @return A redirect to the account balance page for the logged-in user.
	 */
	@PostMapping("/openAccountBalance")
	public String openAccountBalance() {
		return "redirect:/accountBalance/"+logginUsername;
	}
	
	/**
	 * Handler method to open the market page for the logged-in user.
	 * 
	 * @return A redirect to the market page for the logged-in user.
	 */
	@PostMapping("/openMarket")
	public String openMarket() {
		return "redirect:/market/"+logginUsername;
	}
	
	/**
	 * Handler method to exit the application and return to the login page.
	 * 
	 * @return A redirect to the login page.
	 */
	@PostMapping("/exit")
	public String exit() {
		return "redirect:/";
	}	
	
}
