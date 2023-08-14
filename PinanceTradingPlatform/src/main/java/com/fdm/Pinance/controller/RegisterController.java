package com.fdm.Pinance.controller;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fdm.Pinance.model.User;
import com.fdm.Pinance.service.AccountBalanceService;
import com.fdm.Pinance.service.UserService;


/**
 * Controller class that handles HTTP requests related to user registration.
 */
@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountBalanceService accountBalanceService;
	
	/**
	 * Handler method to add a new User object to the system during user registration.
	 * 
	 * @param user The User object containing the user's registration details.
	 * @param model The Model object to pass data to the view.
	 * @return The name of the HTML template used to render the registration page or a redirect to the login page.
	 */
	@PostMapping("/addUserObject")
	public String addUserObject(@ModelAttribute User user, Model model){
		String returnValue;
			
		// Check if the username already exists
		if((userService.getUserByUsername(user.getUsername())) != null) {
			model.addAttribute("message", "username already exists");
			returnValue = "registerUser";
		}
		else {
			// Encrypt the user's password before saving it to the database
			BCryptPasswordEncoder bcrypt = new  BCryptPasswordEncoder();
			String encryptedPwd = bcrypt.encode(user.getPassword());
			user.setPassword(encryptedPwd);
			
			// Save the user object to the database
			userService.saveUser(user);
			
			// Create an account balance for the newly registered user
			accountBalanceService.createAccount(user);
			
			returnValue = "redirect:/";	    // Redirect to the login page after successful registration
		}
		
		return(returnValue);
	}	

}
