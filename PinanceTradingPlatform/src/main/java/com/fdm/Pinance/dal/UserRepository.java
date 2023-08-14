package com.fdm.Pinance.dal;

import org.springframework.stereotype.Repository;
import com.fdm.Pinance.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing User entities in the database.
 * This interface extends the JpaRepository, providing basic CRUD operations for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>
{
    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to find.
     * @return The User entity with the specified username, or null if not found.
     */
	User findByUsername(String username);

}
