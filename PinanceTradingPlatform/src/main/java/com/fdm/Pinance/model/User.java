package com.fdm.Pinance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Represents a User entity in the application.
 */
@Entity
public class User {

    @Id
    @Column(length = 20)
    private String username;
    
    @Column(length = 200)
    private String password;
    
    @Column(length = 40)
    private String firstName;
    
    @Column(length = 40)
    private String lastName;
    
    @Column(length = 60)
    private String email;

    /**
     * Default constructor for the User class.
     */
    public User() {
    	super();
    }
    
    /**
     * Constructor for creating a User with provided information.
     *
     * @param username  The unique username of the user.
     * @param password  The password of the user account.
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @param email     The email address of the user.
     */
    public User(String username, String password, String firstName, String lastName, String email) {
    	super();
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username The username to set for the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password The password to set for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the first name of the user.
     *
     * @return The first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName The first name to set for the user.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return The last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName The last name to set for the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email The email address to set for the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the User object.
     *
     * @return A string containing the User object's fields.
     */
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", email=" + email + "]";
	} 
}

