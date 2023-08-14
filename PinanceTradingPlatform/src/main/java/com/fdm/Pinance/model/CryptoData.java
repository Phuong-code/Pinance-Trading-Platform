package com.fdm.Pinance.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents data related to a cryptocurrency.
 */
@Entity
public class CryptoData {
    
    @Id
    private String symbol;
    private BigDecimal price;

    /**
     * Default constructor for the CryptoData class.
     */
	public CryptoData() {
		super();
	}

    /**
     * Constructor for creating a CryptoData object with provided information.
     *
     * @param symbol The symbol or code representing the cryptocurrency.
     * @param price  The price of the cryptocurrency.
     */
	public CryptoData(String symbol, BigDecimal price) {
		super();
		this.symbol = symbol;
		this.price = price;
	}
	
    /**
     * Gets the symbol of the cryptocurrency.
     *
     * @return The symbol representing the cryptocurrency.
     */
    public String getSymbol() {
        return symbol;
    }
    
    /**
     * Sets the symbol of the cryptocurrency.
     *
     * @param symbol The symbol to set for the cryptocurrency.
     */
	public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the price of the cryptocurrency.
     *
     * @return The price of the cryptocurrency.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the cryptocurrency.
     *
     * @param price The price to set for the cryptocurrency.
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Returns a string representation of the CryptoData object.
     *
     * @return A string containing the CryptoData object's fields.
     */
	@Override
	public String toString() {
		return "CryptoData [symbol=" + symbol + ", price=" + price + "]";
	}
       
}
