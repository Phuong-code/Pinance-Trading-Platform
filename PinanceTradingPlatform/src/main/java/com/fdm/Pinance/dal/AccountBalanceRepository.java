package com.fdm.Pinance.dal;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fdm.Pinance.model.AccountBalance;

/**
 * Repository interface for accessing and managing AccountBalance entities in the database.
 * This interface extends the JpaRepository, providing basic CRUD operations for AccountBalance entities.
 */
@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
	
    /**
     * Finds the account balance for a specific user by their username.
     *
     * @param username The username of the user whose account balance to find.
     * @return The AccountBalance entity associated with the specified username.
     */
    @Query("SELECT ab FROM AccountBalance ab WHERE ab.user.username = :username")
    AccountBalance findByUsername(String username);

    /**
     * Retrieves the amount of a specific cryptocurrency held by a user.
     *
     * @param username     The username of the user.
     * @param cryptoSymbol The symbol or code representing the cryptocurrency.
     * @return The amount of the specified cryptocurrency held by the user.
     */
    @Query(value = "CALL GetCryptoAmount(:username, :cryptoSymbol)", nativeQuery = true)
    BigDecimal getCryptoAmount(@Param("username") String username, @Param("cryptoSymbol") String cryptoSymbol);

    /**
     * Updates the amount of a specific cryptocurrency held by a user.
     *
     * @param username     The username of the user.
     * @param cryptoSymbol The symbol or code representing the cryptocurrency.
     * @param amount       The new amount of the cryptocurrency to be updated.
     */
    @Procedure(name = "updateCryptoAmount")
    void updateCryptoAmount(@Param("username") String username, @Param("cryptoSymbol") String cryptoSymbol, @Param("amount") BigDecimal amount);

    
//  This getCryptoAmount method can be more optimal 
//  reduce the need of setting up stored procedure (Work on progress)
//  @Query(value = "SET @columns = NULL;" +
//  "SELECT GROUP_CONCAT(CONCAT('`', COLUMN_NAME, '`')) INTO @columns " +
//  "FROM INFORMATION_SCHEMA.COLUMNS " +
//  "WHERE TABLE_SCHEMA = 'pinance_database' " +
//  "AND TABLE_NAME = 'account_balance' " +
//  "AND COLUMN_NAME LIKE %:symbolCrypto%;" +
//  "SET @query = CONCAT('SELECT ', @columns, ' FROM account_balance ab WHERE ab.username = :username');" +
//  "PREPARE stmt FROM @query;" +
//  "EXECUTE stmt;" +
//  "DEALLOCATE PREPARE stmt;",
//nativeQuery = true)
//BigDecimal getCryptoAmount(@Param("username") String username, @Param("symbolCrypto") String symbolCrypto);
}

