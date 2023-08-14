package com.fdm.Pinance.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdm.Pinance.model.CryptoData;

/**
 * Repository interface for accessing and managing CryptoData entities in the database.
 * This interface extends the JpaRepository, providing basic CRUD operations for CryptoData entities.
 */
@Repository
public interface CryptoDataRepository extends JpaRepository<CryptoData, String>{

}
