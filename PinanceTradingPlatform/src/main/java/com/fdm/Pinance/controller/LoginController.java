package com.fdm.Pinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdm.Pinance.service.UserService;

/**
 * Controller class that handles HTTP requests related to user login and registration.
 */
@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * Default mapping to the login page.
	 * 
	 * @param model The model object to pass data to the view.
	 * @return The name of the HTML template used to render the login page.
	 */
	@GetMapping("/")
	public String openDefault(Model model) {

		return "login";			// change default to login
	}

	/**
	 * Handler method to display the user registration page.
	 * 
	 * @return The name of the HTML template used to render the user registration page.
	 */	
	@GetMapping("/register")
	public String showRegisterPage() {
	    return "registerUser";
	}

	/**
	 * Handler method to process user login.
	 * 
	 * @param username The username entered by the user.
	 * @param password The password entered by the user.
	 * @param model    The model object to pass data to the view.
	 * @return If the login credentials are valid, redirect to the main page for the user.
	 *         Otherwise, return to the login page with an error message.
	 */
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, Model model) {
	    // Check if the username and password match the database
	    boolean isValidCredentials = userService.isValidCredentials(username, password);

	    if (isValidCredentials) {
	        // Redirect to the main page for the logged-in user
	        return "redirect:/mainPage/"+username;
	    } else {
	        // Display error message for invalid credentials
	        model.addAttribute("message", "Invalid username or password");
	        return "login";
	    }
	}

}
