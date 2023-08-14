package com.fdm.Pinance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fdm.Pinance.dal.UserRepository;
import com.fdm.Pinance.model.User;

/**
 * Service class that provides business logic and operations for managing User entities.
 */
@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
    /**
     * Retrieves a User entity by their username.
     *
     * @param userName The username of the user to retrieve.
     * @return The User entity with the specified username, or null if not found.
     */
	public User getUserByUsername(String userName) {
		Optional<User> user = userRepository.findById(userName);
		if(user.isPresent())			
			return user.get();
		else 
			return null;
	}
	
    /**
     * Retrieves a list of all User entities.
     *
     * @return A list of all User entities in the database.
     */
	public List<User> getAllUsers(){	
		return userRepository.findAll(); 
	}

    /**
     * Saves a new User entity or updates an existing one.
     *
     * @param user The User entity to save or update.
     */
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
    /**
     * Updates an existing User entity.
     *
     * @param user The User entity to update.
     */
	public  void updateUser(User user) {
		saveUser(user);
	}
	
    /**
     * Deletes a User entity by their username.
     *
     * @param username The username of the user to delete.
     */
	public void deleteUserByUsername(String username) {
		userRepository.deleteById(username);
	}
	
    /**
     * Deletes all User entities from the database.
     *
     * @return The number of User entities deleted.
     */
	public int deleteAllUers() {
		
		int count = 0;
		List<User> users = getAllUsers();
		
		for(User user : users) {
			userRepository.delete(user);
			count++;
		}	
		return count;	
	}
    
    /**
     * Checks if the provided username and password match a valid user's credentials.
     *
     * @param username The username to validate.
     * @param password The password to validate.
     * @return true if the provided credentials match a valid user, false otherwise.
     */
    public boolean isValidCredentials(String username, String password) {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        Optional<User> opUser = userRepository.findById(username);

        if (opUser.isPresent()) {
            User dbUser = opUser.get();
            if (bcrypt.matches(password, dbUser.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
